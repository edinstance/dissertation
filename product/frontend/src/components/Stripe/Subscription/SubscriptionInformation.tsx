"use client";
import { isUserSubscribed } from "@/actions/userSubscriptions";
import { GET_USER_BILLING } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import { useEffect, useState } from "react";
import Stripe from "stripe";
import SubscriptionForm from "./SubscriptionForm";
import { UnsubscribeButton } from "./UnsubscribeButton";

function SubscriptionInformation({ stripeKey }: { stripeKey: string }) {
  const [existingSubscription, setExistingSubscription] =
    useState<Stripe.Subscription | null>(null);

  const userBilling = useQuery(GET_USER_BILLING);
  const customerId = userBilling.data?.getUserBilling?.customerId;

  useEffect(() => {
    const checkSubscription = async () => {
      try {
        const res = await isUserSubscribed({ customerId: customerId || "" });
        console.log("User subscription status:", res.isSubscribed);
        setExistingSubscription(res.subscription ?? null);
      } catch (error) {
        console.error("Error checking subscription:", error);
      }
    };

    checkSubscription();
  }, [customerId]);
  return (
    <div>
      {existingSubscription?.status == "active" ? (
        <div>
          <h1 className="text-xl font-bold">Your Subscription</h1>
          <p>Status: {existingSubscription?.status}</p>
          <p>
            Start Date:{" "}
            {new Date(
              existingSubscription?.start_date * 1000,
            ).toLocaleDateString()}
          </p>
          <p>
            Next Billing Date:{" "}
            {new Date(
              existingSubscription.current_period_end * 1000,
            ).toLocaleDateString()}
          </p>
          <div className="pt-4">
            <UnsubscribeButton />
          </div>
        </div>
      ) : (
        <SubscriptionForm stripeKey={stripeKey} />
      )}
    </div>
  );
}

export default SubscriptionInformation;