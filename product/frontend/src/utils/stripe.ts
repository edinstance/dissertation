import stripe from "@/lib/stripe";
import Stripe from "stripe";

// Find customer by userId
export async function findCustomerByUserId(
  userId: string,
): Promise<Stripe.Customer | null> {
  const customers = await stripe.customers.search({
    query: `metadata['userId']: '${userId}'`,
  });

  return customers.data.length > 0 && customers.data[0]
    ? customers.data[0]
    : null;
}

// Get all subscriptions for a customer and return the most recent one
async function getCustomerSubscriptions(
  customerId: string,
): Promise<Stripe.Subscription[]> {
  const subscriptions = await stripe.subscriptions.list({
    customer: customerId,
    status: "all", // To get all subscription statuses
    expand: ["data.latest_invoice.payment_intent"],
  });

  // Sort subscriptions by creation date in descending order (most recent first)
  subscriptions.data.sort((a, b) => b.created - a.created);

  return subscriptions.data;
}

// Find the most recent valid subscription (with a valid payment intent)
function findExistingSubscription(
  subscriptions: Stripe.Subscription[],
): Stripe.Subscription | null {
  return (
    subscriptions.find(
      (sub) =>
        typeof sub.latest_invoice === "object" &&
        sub.latest_invoice?.payment_intent,
    ) || null
  );
}

// Find the most recent subscription for a given userId
export async function findExistingSubscriptionByUserId(
  userId: string,
): Promise<Stripe.Subscription | null> {
  const customer = await findCustomerByUserId(userId);
  if (customer) {
    const subscriptions = await getCustomerSubscriptions(customer.id);
    return findExistingSubscription(subscriptions);
  }
  return null;
}
