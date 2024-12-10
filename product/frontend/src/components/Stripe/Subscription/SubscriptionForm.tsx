"use client";

import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import SubscriptionElements from "./SubscriptionElements";

export default function SubscriptionForm({ stripeKey }: { stripeKey: string }) {
  const stripePromise = loadStripe(stripeKey);
  const session = useSession();
  const [clientSecret, setClientSecret] = useState();

  useEffect(() => {
    const fetchClientSecret = async () => {
      try {
        const response = await fetch("/api/billing/subscriptions", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ userId: session.data?.user?.id }),
        });

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
  }, []);

  const options = {
    clientSecret: clientSecret,
  };

  return (
    <div className="pt-8">
      {clientSecret ? (
        <Elements stripe={stripePromise} options={options}>
          <SubscriptionElements />
        </Elements>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}
