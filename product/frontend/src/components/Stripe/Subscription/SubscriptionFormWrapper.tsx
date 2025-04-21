import SubscriptionForm from "./SubscriptionForm";

function SubscriptionFormWrapper() {
  const stripeKey = process.env.STRIPE_PUBLISHABLE_KEY!;
  return <SubscriptionForm stripeKey={stripeKey} />;
}

export default SubscriptionFormWrapper;
