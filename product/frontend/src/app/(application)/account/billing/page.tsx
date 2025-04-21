import SubscriptionInformation from "@/components/Stripe/Subscription/SubscriptionInformation";

export default function CheckoutPage() {
  const stripeKey = process.env.STRIPE_PUBLISHABLE_KEY;

  if (!stripeKey) {
    throw new Error("Stripe publishable key is not defined");
  }

  return (
    <div className="pt-8 text-black dark:text-white">
      <SubscriptionInformation stripeKey={stripeKey} />
    </div>
  );
}
