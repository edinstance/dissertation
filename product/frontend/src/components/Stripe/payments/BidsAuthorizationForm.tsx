"use client";

import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import {
  PaymentElement,
  useElements,
  useStripe,
} from "@stripe/react-stripe-js";
import { FormEvent, useState } from "react";
import { toast } from "react-toastify";

const BidsAuthorizationForm = ({
  bidAmount,
  itemId,
  onSuccess,
  onError,
  onCancel,
}: {
  bidAmount: number;
  itemId: string;
  onSuccess: () => Promise<void>;
  onError: (message: string) => void;
  onCancel: () => void;
}) => {
  const stripe = useStripe();
  const elements = useElements();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!stripe || !elements) {
      console.error("Stripe.js has not loaded yet.");
      onError("Payment system is not ready. Please try again.");
      return;
    }

    setIsSubmitting(true);
    toast.info("Authorizing your payment method...");

    const { error } = await stripe.confirmSetup({
      elements,
      confirmParams: {
        return_url: `${window.location.origin}/shop/item/${itemId}?bid_status=pending`,
      },
      redirect: "if_required",
    });

    if (error) {
      console.error("Stripe Setup confirmation error:", error);
      toast.dismiss();
      onError(error.message || "Payment authorization failed.");
      setIsSubmitting(false);
    } else {
      console.log("Stripe SetupIntent confirmed successfully.");
      toast.dismiss();
      try {
        await onSuccess();
      } catch (e) {
        console.error("Error during onSuccess callback:", e);
      } finally {
        setIsSubmitting(false);
      }
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="mt-4 space-y-4 border-t pt-4 dark:border-zinc-700"
    >
      <p className="text-sm font-medium text-gray-900 dark:text-white">
        Authorize Bid of £{bidAmount.toFixed(2)}
      </p>
      <div>
        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
          Payment Details
        </label>
        <div className="border-input bg-background mt-1 rounded-md border p-3">
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
        </div>
      </div>
      <div className="flex justify-end gap-3">
        <Button type="button" onClick={onCancel} disabled={isSubmitting}>
          Cancel
        </Button>
        <Button type="submit" disabled={!stripe || !elements || isSubmitting}>
          {isSubmitting ? (
            <LoadingSpinner />
          ) : (
            `Authorize Bid (£${bidAmount.toFixed(2)})`
          )}
        </Button>
      </div>
    </form>
  );
};
export default BidsAuthorizationForm;
