import { Item } from "@/gql/graphql";
import { GET_BIDS_BY_ITEM, SUBMIT_BID_MUTATION } from "@/lib/graphql/bids";
import { useMutation, useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import { useState } from "react";
import { toast } from "react-toastify";
import { v4 as uuidv4 } from "uuid";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";

function BidSidebar({ isOpen, item }: { isOpen: boolean; item: Item }) {
  if (!item || !item.id) {
    return null;
  }

  const session = useSession();
  const userId = session?.data?.user?.id ?? null;

  const [bidAmount, setBidAmount] = useState(0);

  const existingBidsQuery = useQuery(GET_BIDS_BY_ITEM, {
    variables: {
      itemId: item.id,
    },
    fetchPolicy: "cache-and-network",
    onCompleted: (data) => {
      const loadedBids = data?.getItemBidsById;
      if (loadedBids !== undefined) {
        const highest = loadedBids?.at(0)?.amount ?? item.price ?? 0;
        const suggestedNextBid = Math.ceil(highest) + 10;

        setBidAmount(Number(suggestedNextBid.toFixed(2)));
      }
    },
  });

  const [submitBidMutation] = useMutation(SUBMIT_BID_MUTATION, {
    refetchQueries: [
      {
        query: GET_BIDS_BY_ITEM,
        variables: {
          itemId: item.id,
        },
      },
    ],
  });

  const { data, loading, error } = existingBidsQuery;
  const existingBids = data?.getItemBidsById;

  const currentHighestBid = existingBids?.at(0)?.amount ?? null;
  const startingPrice = item.price ?? 0;
  const minimumNextBid = currentHighestBid ?? startingPrice;

  const handleBidSubmit = () => {
    if (isNaN(bidAmount) || bidAmount <= 0) {
      toast.error("Invalid bid amount. Please enter a positive number.");
      return;
    }

    if (bidAmount < minimumNextBid) {
      toast.error(
        `Bid must be at least £${minimumNextBid.toFixed(
          2,
        )}. Current highest is £${(currentHighestBid ?? startingPrice).toFixed(
          2,
        )}.`,
      );
      return;
    }
    submitBidMutation({
      variables: {
        bid: {
          bidId: uuidv4(),
          userId: userId,
          itemId: item.id,
          amount: bidAmount,
        },
      },
      onCompleted: (data) => {
        console.log("Bid submitted:", data);

        if (data?.submitBid?.success) {
          toast.success("Bid submitted successfully!");
        } else {
          toast.error(data?.submitBid?.message ?? "Bid submission failed.");
        }
      },
      onError: (error) => {
        toast.error(`Error: ${error.message}`);
      },
    }).catch((err) => {
      toast.error(`Unexpected error: ${err.message}`);
    });
  };

  return (
    <div
      className={`md:pt-30 fixed inset-y-0 right-0 z-40 w-72 transform border-l border-zinc-300 bg-zinc-100 p-6 pt-40 transition-transform duration-300 ease-in-out dark:border-zinc-700 dark:bg-zinc-800 md:relative md:inset-y-auto md:z-auto md:w-1/4 md:min-w-[280px] md:max-w-xs md:translate-x-0 md:border-l md:pt-6 ${
        isOpen ? "translate-x-0" : "translate-x-full"
      }`}
    >
      <div className="flex h-full flex-col gap-6 text-black dark:text-white">
        <h2 className="text-center text-2xl font-bold">Bid for {item.name}</h2>

        {loading && <div className="text-center">Loading bid info...</div>}
        {error && (
          <div className="text-center text-red-500">Error loading bids.</div>
        )}

        {!loading && !error && (
          <div className="text-center">
            <p className="text-lg text-gray-600 dark:text-gray-400">
              Current Highest Bid: £
              <span className="text-black dark:text-white">
                {currentHighestBid?.toFixed(2) ?? "0.00"}
              </span>
            </p>
            <p className="mt-1 text-xs text-gray-500">
              (Starting Price: £{startingPrice.toFixed(2)})
            </p>
          </div>
        )}

        <div className="flex flex-col items-center gap-3">
          <label htmlFor="bidAmount" className="sr-only">
            Bid Amount
          </label>
          <div className="relative w-full">
            <div className="pointer-events-none absolute inset-y-0 left-3 flex items-center">
              <span className="text-gray-500">£</span>
            </div>
            <Input
              id="bidAmount"
              type="number"
              value={bidAmount}
              placeholder={
                loading ? "Loading..." : `Min £${minimumNextBid.toFixed(2)}`
              }
              onChange={(e) => setBidAmount(Number(e.target.value))}
              className="w-full pl-7 text-center"
              step="5"
              min={minimumNextBid.toFixed(2)}
              disabled={loading || !!error}
            />
          </div>
          <Button
            onClick={handleBidSubmit}
            className="w-full"
            disabled={loading || !!error}
          >
            Place Bid
          </Button>
        </div>

        <div className="mt-4 flex-1 overflow-y-auto border-t pt-4 dark:border-zinc-700">
          <h3 className="mb-2 text-lg font-semibold">Bid History</h3>
          {loading && <LoadingSpinner />}
          {error && <p className="text-red-500">Could not load bid history.</p>}
          {!loading && !error && existingBids && existingBids.length > 0 ? (
            <div className="space-y-2">
              {existingBids.map(
                (bid, index) =>
                  bid && (
                    <div
                      key={index}
                      className={`rounded p-2 ${
                        index === 0
                          ? "border border-blue-300 bg-blue-100 dark:border-blue-700 dark:bg-blue-900"
                          : "bg-zinc-200 dark:bg-zinc-700"
                      }`}
                    >
                      <div className="flex justify-between">
                        <span className="text-sm font-medium">
                          £{bid.amount?.toFixed(2)}
                        </span>
                        <span className="text-xs text-gray-500 dark:text-gray-400">
                          {bid.createdAt
                            ? new Date(bid.createdAt).toLocaleString()
                            : null}
                        </span>
                      </div>
                    </div>
                  ),
              )}
            </div>
          ) : (
            !loading &&
            !error && <p className="text-sm text-gray-500">No previous bids.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default BidSidebar;
