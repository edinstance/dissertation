"use client";
import { isUserSubscribed } from "@/actions/userSubscriptions";
import SubscriptionFormWrapper from "@/components/Stripe/Subscription/SubscriptionFormWrapper";
import { UnsubscribeButton } from "@/components/Stripe/Subscription/UnsubscribeButton";
import { GET_USER_BILLING } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import { useEffect, useState } from "react";
import Stripe from "stripe";

/**
 * CheckoutPage component for managing user subscriptions.
 *
 * This component checks if the user has an existing subscription. If the user
 * has an active subscription, it displays the subscription details and an option
 * to unsubscribe. If not, it renders the SubscriptionForm for the user to create
 * a new subscription.
 *
 * @returns A promise that resolves to the rendered CheckoutPage component.
 */
export default function CheckoutPage() {
  const [existingSubscription, setExistingSubscription] =
    useState<Stripe.Subscription | null>(null);

  const userBilling = useQuery(GET_USER_BILLING);
  const customerId = userBilling.data?.getUserBilling?.customerId;

  useEffect(() => {
    const checkSubscription = async () => {
      try {
        const res = await isUserSubscribed({ customerId: customerId || "" });
        setExistingSubscription(res.subscription ?? null);
      } catch (error) {
        console.error("Error checking subscription:", error);
      }
    };

    checkSubscription();
  }, [customerId]);

  return (
    <div className="pt-8 text-black dark:text-white">
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
        <SubscriptionFormWrapper />
      )}
    </div>
  );
}
