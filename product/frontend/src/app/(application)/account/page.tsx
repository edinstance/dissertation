"use client";
import { isUserSubscribed } from "@/actions/userSubscriptions";
import ReportBugForm from "@/components/reports/ReportBugForm";
import { Button } from "@/components/ui/Button";
import Divider from "@/components/ui/Divider";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import Modal from "@/components/ui/Modal";
import DeleteUserInformation from "@/components/Users/DeleteUserInformation";
import {
  CHECK_USER_DETAILS,
  GET_USER_BILLING,
  SAVE_USER_BILLING_MUTATION,
} from "@/lib/graphql/users";
import { useMutation, useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import Link from "next/link";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";

/**
 * Account component for managing user account settings.
 *
 * This component allows users to delete their account. It displays a button
 * that opens a confirmation modal when clicked. If the user confirms the
 * deletion, the account is deleted, and the user is signed out.
 *
 * @returns The rendered Account component.
 */
export default function Account() {
  const [open, setOpen] = useState(false);
  const [reportBugModalOpen, setReportBugModalOpen] = useState(false);
  const [isSubscribed, setIsSubscribed] = useState<boolean | null>(null);
  const [isLoadingSubscription, setIsLoadingSubscription] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const session = useSession();
  const userId = session.data?.user?.id;

  const userBilling = useQuery(GET_USER_BILLING);
  const customerId = userBilling.data?.getUserBilling?.customerId;

  const [saveUserBillingMutation] = useMutation(SAVE_USER_BILLING_MUTATION);

  useEffect(() => {
    if (userId) {
      setIsLoadingSubscription(true);
      const checkSubscription = async () => {
        try {
          const res = await isUserSubscribed({ customerId: customerId || "" });
          setIsSubscribed(res.isSubscribed ?? false);
          console.log("User subscription status:", res.isSubscribed);
        } catch (error) {
          console.error("Error checking subscription:", error);
          setIsSubscribed(null);
        } finally {
          setIsLoadingSubscription(false);
        }
      };

      checkSubscription();
    } else {
      setIsSubscribed(false);
      setIsLoadingSubscription(false);
    }
  }, [userId, customerId]);

  async function handleConnectStripe() {
    try {
      setIsLoading(true);

      const response = await fetch("/api/billing/accounts/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          accountId: userBilling.data?.getUserBilling?.accountId ?? null,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(
          data.error?.message || "Failed to initiate onboarding.",
        );
      }

      saveUserBillingMutation({
        variables: {
          input: {
            userId: userId,
            customerId: customerId,
            accountId: data.accountId,
          },
        },
      });

      if (data.url) {
        window.location.href = data.url;
      } else {
        console.error("No URL returned from Stripe:", data);
        toast.error("Failed to redirect to Stripe. Please try again.");
      }
    } catch (error) {
      console.error("Error during Stripe Connect setup:", error);
      toast.error(
        "An error occurred while setting up Stripe Connect. Please try again.",
      );
    } finally {
      setIsLoading(false);
    }
  }

  async function handleLoginToStripe() {
    try {
      setIsLoading(true);

      const response = await fetch(
        `/api/billing/accounts/getLoginLink?accountId=${userBilling.data?.getUserBilling?.accountId ?? null}`,
        {
          method: "GET",
        },
      );

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.error?.message || "Failed to get link.");
      }

      if (data.url) {
        window.open(data.url, "_blank")?.focus();
      } else {
        console.error("No URL returned from Stripe:", data);
        toast.error("Failed to redirect to Stripe. Please try again.");
      }
    } catch (error) {
      console.error("Error during Stripe Connect viewing:", error);
      toast.error(
        "An error occurred while getting your account link. Please try again.",
      );
    } finally {
      setIsLoading(false);
    }
  }

  const { data: userDetailsData, loading: userDetailsLoading } =
    useQuery(CHECK_USER_DETAILS);

  const userDetailsExist = userDetailsData?.checkCurrentUserDetailsExist;

  if (isLoading) {
    return (
      <div className="flex h-screen items-center justify-center">
        <LoadingSpinner />
      </div>
    );
  }

  return (
    <div className="space-y-4 pt-8 text-black dark:text-white">
      <h1 className="text-xl font-bold">Account Details</h1>
      <p className="text-muted-foreground pb-4 text-sm">
        You can manage your account settings here.
      </p>
      <Divider />
      <div className="flex items-center gap-2">
        <span className="font-medium">Details status:</span>
        {userDetailsLoading ? (
          <span className="text-muted-foreground">Loading...</span>
        ) : (
          <div className="flex items-center gap-2">
            <span
              className={`font-semibold ${userDetailsExist ? "text-green-500 dark:text-green-400" : "text-amber-500 dark:text-amber-400"}`}
            >
              {userDetailsExist ? "Completed" : "Not Completed"}
            </span>
            {!userDetailsExist && (
              <Link
                href="/account/details"
                className="text-primary hover:text-primary/80 underline transition-colors"
              >
                Fill in your details
              </Link>
            )}
          </div>
        )}
      </div>
      <div>
        <div className="flex items-center gap-2">
          <span className="font-medium">Subscription status:</span>
          {isLoadingSubscription ? (
            <span className="text-muted-foreground">Loading...</span>
          ) : (
            <div className="flex items-center gap-2">
              <span
                className={`font-semibold ${isSubscribed ? "text-green-500 dark:text-green-400" : "text-amber-500 dark:text-amber-400"}`}
              >
                {isSubscribed ? "Active" : "Inactive"}
              </span>
              {!isSubscribed && (
                <Link
                  href="/account/billing"
                  className="text-primary hover:text-primary/80 underline transition-colors"
                >
                  Upgrade
                </Link>
              )}
            </div>
          )}
        </div>
      </div>
      {isSubscribed && userDetailsExist && (
        <div className="flex items-center gap-2">
          <span className="font-medium">Seller status:</span>
          <Button className="text-sm" onClick={handleConnectStripe}>
            {userBilling.data?.getUserBilling?.accountId
              ? "Update account"
              : "Connect to Stripe"}
          </Button>

          {userBilling.data?.getUserBilling?.accountId && (
            <Button className="text-sm" onClick={handleLoginToStripe}>
              View account
            </Button>
          )}
        </div>
      )}
      <Divider className="py-4" />
      <div className="flex flex-row items-center space-x-4">
        <Button
          onClick={() => {
            setReportBugModalOpen(true);
            setOpen(true);
          }}
        >
          Report an issue
        </Button>
        <Button
          onClick={() => {
            setReportBugModalOpen(false);
            setOpen(true);
          }}
          color="destructive"
        >
          Delete User
        </Button>
        <Modal open={open} setOpen={setOpen}>
          {reportBugModalOpen ? (
            <ReportBugForm setOpen={setOpen} />
          ) : (
            <DeleteUserInformation setOpen={setOpen} />
          )}
        </Modal>
      </div>
    </div>
  );
}
