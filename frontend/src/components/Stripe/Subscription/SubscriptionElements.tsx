"use client";
import { GET_USER } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import {
  AddressElement,
  PaymentElement,
  useElements,
  useStripe,
} from "@stripe/react-stripe-js";
import { useState } from "react";
import { Button } from "../../ui/Button";

export default function SubscriptionElements({
  subscriptionId,
}: {
  subscriptionId?: string | null;
}) {
  const userResponse = useQuery(GET_USER);
  const user = userResponse?.data?.getUser;

  const stripe = useStripe();
  const elements = useElements();
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!stripe || !elements) {
      setErrorMessage("Stripe.js has not loaded.");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage(null);

    try {
      // Ensure we're not appending subscription_id if it's null or undefined
      const returnUrl = subscriptionId
        ? `${window.location.origin}/account/billing?subscription_id=${subscriptionId}`
        : `${window.location.origin}/account/billing`;

      const { error } = await stripe.confirmPayment({
        elements,
        confirmParams: {
          return_url: returnUrl,
        },
      });

      if (error) {
        setErrorMessage(error.message || "Payment failed");
        console.error("Payment failed:", error);
      }
    } catch (e) {
      setErrorMessage("An unexpected error occurred during payment processing");
      console.error("Stripe confirmation error:", e);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="mb-4 rounded-lg px-8 py-4 pb-8 shadow-md"
    >
      <p className="pb-4 text-xl text-black dark:text-white">
        Pay for your subscription
      </p>
      <PaymentElement
        options={{
          layout: {
            type: "accordion",
            defaultCollapsed: false,
            radios: false,
            spacedAccordionItems: true,
          },
        }}
      />

      <div className="pt-8">
        <p className="pb-4 text-xl text-black dark:text-white">
          Billing Information
        </p>
        <AddressElement
          options={{
            mode: "billing",
            defaultValues: {
              name: user?.name || "",
              address: {
                line1: user?.details?.houseName || "",
                line2: user?.details?.addressStreet || "",
                city: user?.details?.addressCity || "",
                state: user?.details?.addressCounty || "",
                postal_code: user?.details?.addressPostcode || "",
                country: "GB",
              },
            },
          }}
        />
      </div>

      {errorMessage && (
        <div className="mt-4 text-sm text-red-500">{errorMessage}</div>
      )}
      <div className="flex justify-end pt-4">
        <Button
          type="submit"
          disabled={!stripe || isSubmitting}
          className="min-w-3xl"
        >
          {isSubmitting ? "Processing..." : "Pay"}
        </Button>
      </div>
    </form>
  );
}
