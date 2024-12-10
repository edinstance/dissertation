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

export default function SubscriptionElements() {
  const userResponse = useQuery(GET_USER);
  const user = userResponse?.data?.getUser;
  console.log(user);

  const stripe = useStripe();
  const elements = useElements();
  const [errorMessage] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) {
      console.error("Stripe.js has not loaded.");
      return;
    }

    setIsSubmitting(true);

    const { error } = await stripe.confirmPayment({
      elements,
      confirmParams: {
        return_url: "http://localhost:3000/account/billing",
      },
    });

    if (error) {
      console.error("Payment failed:", error.message);
    }

    setIsSubmitting(false);
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="mb-4 rounded-lg px-8 py-4 pb-8 shadow-md"
    >
      <p className="pb-4 text-xl">Pay for your subscription</p>
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
        <p className="pb-4 text-xl">Billing Information</p>
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
