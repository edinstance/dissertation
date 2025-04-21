"use client";
import { isUserSubscribed } from "@/actions/userSubscriptions";
import ReportBugForm from "@/components/reports/ReportBugForm";
import { Button } from "@/components/ui/Button";
import Divider from "@/components/ui/Divider";
import Modal from "@/components/ui/Modal";
import DeleteUserInformation from "@/components/Users/DeleteUserInformation";
import { CHECK_USER_DETAILS, GET_USER_BILLING } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import Link from "next/link";
import { useEffect, useState } from "react";

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

  const session = useSession();
  const userId = session.data?.user?.id;

  const userBilling = useQuery(GET_USER_BILLING);
  const customerId = userBilling.data?.getUserBilling?.customerId;

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

  const { data: userDetailsData, loading: userDetailsLoading } =
    useQuery(CHECK_USER_DETAILS);

  const userDetailsExist = userDetailsData?.checkCurrentUserDetailsExist;

  return (
    <div className="space-y-4 pt-8 text-black dark:text-white">
      <h1 className="text-xl font-bold">Account Details</h1>
      <p className="text-muted-foreground pb-4 text-sm">
        You can manage your account settings here.
      </p>
      <Divider />
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
      {isSubscribed && userDetailsExist && (
        <div className="flex items-center gap-2">
          <span className="font-medium">Seller status:</span>
          <span className="text-muted-foreground">
            You are subscribed to the premium plan.
          </span>
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
