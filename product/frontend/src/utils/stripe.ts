import Stripe from "stripe";

/**
 * Finds a Stripe customer by their user ID.
 *
 * @param userId - The user ID associated with the customer.
 * @returns A promise that resolves to the Stripe customer object if found, or null if not found.
 */
export async function findCustomerByUserId(
  userId: string,
): Promise<Stripe.Customer | null> {
  const stripe = new Stripe(process.env.STRIPE_SECRET_KEY as string);

  const customers = await stripe.customers.search({
    query: `metadata['userId']: '${userId}'`,
  });

  return customers.data.length > 0 && customers.data[0]
    ? customers.data[0]
    : null;
}

/**
 * Retrieves all subscriptions for a given customer and returns them sorted by creation date.
 *
 * @param customerId - The ID of the customer whose subscriptions are to be retrieved.
 * @returns A promise that resolves to an array of subscriptions for the customer.
 */
async function getCustomerSubscriptions(
  customerId: string,
): Promise<Stripe.Subscription[]> {
  const stripe = new Stripe(process.env.STRIPE_SECRET_KEY as string);
  const subscriptions = await stripe.subscriptions.list({
    customer: customerId,
    status: "all", // To get all subscription statuses
    expand: ["data.latest_invoice.payment_intent"],
  });

  // Sort subscriptions by creation date in descending order (most recent first)
  subscriptions.data.sort((a, b) => b.created - a.created);

  return subscriptions.data;
}

/**
 * Finds the most recent valid subscription for a given array of subscriptions.
 *
 * @param subscriptions - An array of subscriptions to search through.
 * @returns The most recent valid subscription with a valid payment intent, or null if none found.
 */
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

/**
 * Finds the most recent subscription for a given user ID.
 *
 * @param userId - The user ID associated with the customer.
 * @returns A promise that resolves to the most recent valid subscription for the user, or null if not found.
 */
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
