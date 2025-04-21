"use client";

import LoadingSpinner from "@/components/ui/LoadingSpinner";
import { GET_USER_BILLING } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import { Elements } from "@stripe/react-stripe-js";
import { Appearance, loadStripe } from "@stripe/stripe-js";
import { useTheme } from "next-themes";
import { useEffect, useMemo, useState } from "react";
import SubscriptionElements from "./SubscriptionElements";

/**
 * SubscriptionForm component for managing user subscriptions.
 *
 * This component fetches the client secret from the server and renders the
 * Stripe Elements for subscription management. It displays a loading spinner
 * while the client secret is being fetched.
 *
 * @param props - The props for the component.
 * @returns The rendered SubscriptionForm component.
 */
export default function SubscriptionForm({ stripeKey }: { stripeKey: string }) {
  const stripePromise = loadStripe(stripeKey);

  const [clientSecret, setClientSecret] = useState();
  const { resolvedTheme } = useTheme();

  const userBilling = useQuery(GET_USER_BILLING);
  const customerId = userBilling.data?.getUserBilling?.customerId;

  useEffect(() => {
    const fetchClientSecret = async () => {
      if (!customerId) return;

      try {
        const response = await fetch("/api/billing/subscriptions", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ customerId: customerId }),
        });
        console.log("Response:", response);
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const data = await response.json();
        console.log("Fetched client secret:", data.client_secret);
        const { client_secret } = data;
        setClientSecret(client_secret);
      } catch (error) {
        console.error("Error fetching client secret:", error);
      }
    };

    fetchClientSecret();
  }, [customerId]);

  const appearance: Appearance | undefined = useMemo(
    () => ({
      theme: resolvedTheme === "dark" ? "night" : "stripe",
    }),
    [resolvedTheme],
  );

  const options = {
    clientSecret: clientSecret,
    appearance,
  };

  return (
    <div className="pt-8">
      {clientSecret ? (
        <Elements stripe={stripePromise} options={options}>
          <SubscriptionElements />
        </Elements>
      ) : (
        <LoadingSpinner />
      )}
    </div>
  );
}
