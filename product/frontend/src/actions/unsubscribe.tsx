"use server";

import stripe from "@/lib/stripe";
import { findExistingSubscriptionByUserId } from "@/utils/stripe";

export async function unsubscribe({ id }: { id: string }) {
  try {
    // Fetch the existing subscription
    const subscription = await findExistingSubscriptionByUserId(id);
    if (!subscription) {
      return { error: "Subscription not found" };
    }

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
