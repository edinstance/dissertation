"use server";

import { findExistingSubscriptionByUserId } from "@/utils/stripe";
import Stripe from "stripe";

/**
 * Unsubscribes a user by canceling their subscription.
 *
 * This function fetches the existing subscription for the user and cancels it
 * using the Stripe API. It returns a success message or an error if the
 * cancellation fails.
 *
 * @param params - The parameters for the unsubscribe operation.
 * @returns A promise that resolves to an object indicating the result of the operation.
 */
export async function unsubscribe({ id }: { id: string }) {
  try {
    // Fetch the existing subscription
    const subscription = await findExistingSubscriptionByUserId(id);
    if (!subscription) {
      return { error: "Subscription not found" };
    }

    const stripe = new Stripe(process.env.STRIPE_SECRET_KEY as string);

    // Cancel the subscription
    const canceledSubscription = await stripe.subscriptions.cancel(
      subscription.id,
    );

    // Return confirmation of cancellation
    return {
      success: true,
      message: `Subscription ${canceledSubscription.id} has been canceled.`,
      status: canceledSubscription.status,
    };
  } catch (error) {
    return {
      error:
        error instanceof Error ? error.message : "Error canceling subscription",
    };
  }
}
