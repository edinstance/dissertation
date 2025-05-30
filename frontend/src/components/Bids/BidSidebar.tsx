"use client";

import { Bid, Item } from "@/gql/graphql";
import { GET_BIDS_BY_ITEM, SUBMIT_BID_MUTATION } from "@/lib/graphql/bids";
import { useMutation, useQuery } from "@apollo/client";
import { Elements } from "@stripe/react-stripe-js";
import {
  Appearance,
  loadStripe,
  StripeElementsOptions,
} from "@stripe/stripe-js";
import { useSession } from "next-auth/react";
import { useTheme } from "next-themes";
import { useCallback, useEffect, useMemo, useState } from "react";
import { toast } from "react-toastify";
import { v4 as uuidv4 } from "uuid";
import BidsAuthorizationForm from "../Stripe/payments/BidsAuthorizationForm";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";
import Modal from "../ui/Modal";
import BidCountdownTimer from "./BidCountdownTimer";

function BidSidebar({
  isOpen,
  item,
  stripeKey,
  bidderStripeCustomerId,
}: {
  isOpen: boolean;
  item: Item;
  stripeKey: string;
  bidderStripeCustomerId: string | null;
}) {
  const session = useSession();
  const userId = session?.data?.user?.id ?? null;
  const { resolvedTheme } = useTheme();

  const [bidAmountInput, setBidAmountInput] = useState<number>(0);
  const [minimumNextBid, setMinimumNextBid] = useState<number>(0);
  const [currentHighestBid, setCurrentHighestBid] = useState<number | null>(
    null,
  );

  const stripePromise = useMemo(() => loadStripe(stripeKey), [stripeKey]);
  const [clientSecret, setClientSecret] = useState<string | null>(null);
  const [isProcessingIntent, setIsProcessingIntent] = useState(false);
  const [isSubmittingBid, setIsSubmittingBid] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const itemId = item.id ?? "";

  const existingBidsQuery = useQuery(GET_BIDS_BY_ITEM, {
    variables: { itemId },
    fetchPolicy: "cache-and-network",
    skip: !itemId,
    onCompleted: (data) => {
      const loadedBids = data?.getItemBidsById;
      const highest = loadedBids?.at(0)?.amount ?? item.price ?? 0;
      const nextMin = Math.max(highest + 0.01, item.price ?? 0);

      setCurrentHighestBid(loadedBids?.at(0)?.amount ?? null);
      setMinimumNextBid(Number(nextMin.toFixed(2)));

      if (bidAmountInput <= 0 || bidAmountInput < nextMin) {
        setBidAmountInput(Number(nextMin.toFixed(2)));
      }
    },
    onError: (error) => {
      console.error("Error fetching bids:", error);
      toast.error("Could not load current bid information.");
    },
  });

  const [submitBidMutation] = useMutation(SUBMIT_BID_MUTATION, {
    refetchQueries: [
      {
        query: GET_BIDS_BY_ITEM,
        variables: {
          itemId: itemId,
        },
      },
    ],
  });

  const {
    data: bidsData,
    loading: bidsLoading,
    error: bidsError,
  } = existingBidsQuery;
  const existingBids = bidsData?.getItemBidsById;
  const startingPrice = item.price ?? 0;

  useEffect(() => {
    if (!isOpen) {
      setClientSecret(null);
      setIsProcessingIntent(false);
      setIsSubmittingBid(false);
      setIsModalOpen(false);
    }
  }, [isOpen, itemId]);
  const handleInitiateAuthorization = useCallback(async () => {
    setClientSecret(null);
    toast.dismiss();

    if (isNaN(bidAmountInput) || bidAmountInput <= 0) {
      toast.error("Invalid bid amount. Please enter a positive number.");
      return;
    }
    if (bidAmountInput < minimumNextBid) {
      toast.error(`Bid must be at least £${minimumNextBid.toFixed(2)}.`);
      return;
    }
    if (!bidderStripeCustomerId) {
      toast.error("Payment information not found. Cannot place bid.");
      return;
    }
    if (!userId) {
      toast.error("You must be logged in to place a bid.");
      return;
    }

    setIsProcessingIntent(true);
    try {
      const response = await fetch("/api/billing/payments", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          customerId: bidderStripeCustomerId,
          amount: Math.round(bidAmountInput * 100),
          itemId: itemId,
        }),
      });

      const responseData = await response.json();

      if (!response.ok || !responseData.clientSecret) {
        throw new Error(
          responseData.error || "Failed to prepare bid authorization.",
        );
      }
      setClientSecret(responseData.clientSecret);
      setIsModalOpen(true);
    } catch (error: unknown) {
      console.error("Failed to create SetupIntent:", error);
      toast.error(
        error instanceof Error
          ? error.message
          : "Could not initiate bid process.",
      );
      setClientSecret(null);
      setIsModalOpen(false);
    } finally {
      setIsProcessingIntent(false);
    }
  }, [bidAmountInput, minimumNextBid, bidderStripeCustomerId, itemId, userId]);

  const finalizeBidSubmission = useCallback(
    async (paymentMethodId: string) => {
      if (!userId) {
        toast.error("User session lost. Cannot submit bid.");
        setClientSecret(null);
        setIsModalOpen(false);
        throw new Error("User session lost");
      }
      if (!paymentMethodId) {
        toast.error(
          "Payment Method ID missing after authorization. Cannot submit bid.",
        );
        setIsModalOpen(false);
        setClientSecret(null);
        throw new Error("Missing PaymentMethod ID");
      }

      setIsSubmittingBid(true);
      toast.info("Submitting your bid...");

      try {
        const { data: mutationData, errors } = await submitBidMutation({
          variables: {
            bid: {
              bidId: uuidv4(),
              userId: userId,
              itemId: itemId,
              amount: bidAmountInput,
              paymentMethod: paymentMethodId,
            },
          },
        });

        if (errors || !mutationData?.submitBid?.success) {
          const errorMessage =
            mutationData?.submitBid?.message ||
            errors?.[0]?.message ||
            "Bid submission failed.";
          throw new Error(errorMessage);
        }

        console.log("Bid submitted via GraphQL:", mutationData);
        toast.success("Bid submitted successfully!");
        setClientSecret(null);
        setIsModalOpen(false);
      } catch (error: unknown) {
        console.error("Error submitting bid via GraphQL:", error);
        toast.error(
          `Bid Submission Error: ${error instanceof Error ? error.message : "Unknown error"}`,
        );
        setIsModalOpen(false);
        setClientSecret(null);
        throw error;
      } finally {
        setIsSubmittingBid(false);
      }
    },
    [userId, submitBidMutation, itemId, bidAmountInput],
  );

  const handleAuthorizationError = (message: string) => {
    toast.error(message);
  };

  const handleCancelAuthorization = () => {
    setClientSecret(null);
    setIsModalOpen(false);
    toast.dismiss();
  };

  const appearance: Appearance | undefined = useMemo(
    () => ({
      theme: resolvedTheme === "dark" ? "night" : "stripe",
    }),
    [resolvedTheme],
  );

  const stripeElementsOptions: StripeElementsOptions | undefined = clientSecret
    ? { clientSecret, appearance }
    : undefined;

  const isLoading = bidsLoading || isProcessingIntent || isSubmittingBid;
  const canPlaceBid = !!bidderStripeCustomerId && !!userId;

  if (bidsLoading && !bidsData) {
    return (
      <aside
        className={`fixed inset-y-0 right-0 z-40 w-72 transform border-l border-zinc-300 bg-zinc-100 p-6 pt-40 transition-transform duration-300 ease-in-out dark:border-zinc-700 dark:bg-zinc-800 md:relative md:inset-y-auto md:z-auto md:w-1/4 md:min-w-[280px] md:max-w-xs md:translate-x-0 md:border-l md:pt-6 ${isOpen ? "translate-x-0" : "translate-x-full md:translate-x-0"}`}
      >
        <div className="flex h-full items-center justify-center">
          <LoadingSpinner />
        </div>
      </aside>
    );
  }

  return (
    <aside
      className={`fixed inset-y-0 right-0 z-40 w-72 transform border-l border-zinc-300 bg-zinc-100 p-6 pt-40 transition-transform duration-300 ease-in-out dark:border-zinc-700 dark:bg-zinc-800 md:relative md:inset-y-auto md:z-auto md:w-1/4 md:min-w-[280px] md:max-w-xs md:translate-x-0 md:border-l md:pt-6 ${isOpen ? "translate-x-0" : "translate-x-full md:translate-x-0"}`}
    >
      <div className="flex h-full flex-col gap-4 text-black dark:text-white">
        <h2 className="text-center text-xl font-bold">Bid for {item.name}</h2>

        {item.endingTime && (
          <div className="mb-1 flex flex-row items-center justify-between gap-2 text-center text-sm">
            <p className="font-medium text-gray-600 dark:text-gray-400">
              Ends in:
            </p>
            <BidCountdownTimer endTime={new Date(item.endingTime)} />
          </div>
        )}

        {bidsError && (
          <div className="text-center text-sm text-red-500">
            Error loading bid info.
          </div>
        )}
        {!bidsError && (
          <div className="text-center text-sm">
            <p className="text-gray-600 dark:text-gray-400">
              Current Bid:{" "}
              <span className="font-semibold text-black dark:text-white">
                £{currentHighestBid?.toFixed(2) ?? startingPrice.toFixed(2)}
              </span>
              {currentHighestBid === null && " (Starting Price)"}
            </p>
            <p className="mt-1 text-xs text-gray-500">
              Min. Next Bid: £{minimumNextBid.toFixed(2)}
            </p>
          </div>
        )}

        <div className="mt-2 border-t pt-4 dark:border-zinc-700">
          <div className="flex flex-col items-center gap-3">
            <label htmlFor="bidAmountInput" className="sr-only">
              Bid Amount
            </label>
            <div className="relative w-full">
              <div className="pointer-events-none absolute inset-y-0 left-3 flex items-center">
                <span className="text-gray-500">£</span>
              </div>
              <Input
                id="bidAmountInput"
                type="number"
                value={bidAmountInput}
                placeholder={`Min £${minimumNextBid.toFixed(2)}`}
                onChange={(e) => setBidAmountInput(Number(e.target.value))}
                className="w-full pl-7 text-center"
                step="10"
                min={minimumNextBid.toFixed(2)}
                disabled={isLoading || !!bidsError || !canPlaceBid}
                aria-label="Bid Amount Input"
              />
            </div>
            <Button
              onClick={handleInitiateAuthorization}
              className="w-full"
              disabled={
                isLoading ||
                !!bidsError ||
                !canPlaceBid ||
                bidAmountInput < minimumNextBid
              }
            >
              {isProcessingIntent ? <LoadingSpinner /> : "Place Bid"}
            </Button>
            {!canPlaceBid && (
              <p className="mt-1 text-center text-xs text-yellow-600">
                {!userId
                  ? "Please log in to bid."
                  : "Please add payment info to bid."}
              </p>
            )}
          </div>
        </div>

        <div className="mt-4 flex-1 overflow-hidden border-t pt-4 dark:border-zinc-700">
          <h3 className="mb-2 text-base font-semibold">Bid History</h3>
          {bidsLoading && existingBids === undefined && <LoadingSpinner />}
          {bidsError && (
            <p className="text-sm text-red-500">Could not load bid history.</p>
          )}
          {!bidsError && existingBids && existingBids.length > 0 ? (
            <div
              className="space-y-2 overflow-y-auto pb-4"
              style={{ maxHeight: "calc(100% - 40px)" }}
            >
              {(existingBids as Bid[]).map(
                (bid, index) =>
                  bid && (
                    <div
                      key={index}
                      className={`rounded p-2 text-xs ${
                        index === 0
                          ? "border border-blue-300 bg-blue-100 dark:border-blue-700 dark:bg-blue-900/50"
                          : "bg-zinc-200 dark:bg-zinc-700/50"
                      }`}
                    >
                      <div className="flex items-center justify-between gap-2">
                        <span className="font-medium">
                          £{bid.amount?.toFixed(2)}
                        </span>
                        <span className="flex-shrink-0 text-gray-500 dark:text-gray-400">
                          {bid.createdAt
                            ? new Date(bid.createdAt).toLocaleDateString()
                            : "Recently"}
                        </span>
                      </div>
                    </div>
                  ),
              )}
            </div>
          ) : (
            !bidsLoading &&
            !bidsError && (
              <p className="text-sm text-gray-500">No previous bids.</p>
            )
          )}
        </div>
      </div>

      {isModalOpen && stripePromise && stripeElementsOptions && (
        <Modal open={isModalOpen} setOpen={setIsModalOpen}>
          <div className="min-w-[320px] sm:min-w-[400px]">
            <Elements stripe={stripePromise} options={stripeElementsOptions}>
              <BidsAuthorizationForm
                bidAmount={bidAmountInput}
                itemId={itemId}
                onSuccess={finalizeBidSubmission}
                onError={handleAuthorizationError}
                onCancel={handleCancelAuthorization}
              />
            </Elements>
          </div>
        </Modal>
      )}
    </aside>
  );
}

export default BidSidebar;
