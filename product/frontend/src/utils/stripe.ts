import stripe from "@/lib/stripe";
import Stripe from "stripe";

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

export async function getCustomerSubscriptions(
  customerId: string,
): Promise<Stripe.Subscription[]> {
  const subscriptions = await stripe.subscriptions.list({
    customer: customerId,
    status: "all",
    expand: ["data.latest_invoice.payment_intent"],
  });

  return subscriptions.data;
}

export function findExistingSubscription(
  subscriptions: Stripe.Subscription[],
): Stripe.Subscription | null {
  return (
    subscriptions.find(
      (sub) => sub.latest_invoice && sub.latest_invoice.payment_intent,
    ) || null
  );
}

export async function findExistingSubscriptionById(
  userId: string,
): Promise<Stripe.Subscription | null> {
  const customer = await findCustomerByUserId(userId);
  if (customer) {
    const subscriptions = await getCustomerSubscriptions(customer.id);
    return findExistingSubscription(subscriptions);
  }
  return null;
}
