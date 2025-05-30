"use server";

import { findExistingSubscriptionByCustomerId } from "@/utils/stripe";

export async function isUserSubscribed({ customerId }: { customerId: string }) {
  try {
    // Fetch the existing subscription
    const subscription = await findExistingSubscriptionByCustomerId(customerId);
    if (!subscription) {
      return { error: "Subscription not found" };
    }
    console.log("Subscription found:", subscription);
    if (subscription.status === "active") {
      return { isSubscribed: true, subscription };
    }
    return { isSubscribed: false };
  } catch (error) {
    return {
      error:
        error instanceof Error ? error.message : "Error checking subscription",
    };
  }
}
