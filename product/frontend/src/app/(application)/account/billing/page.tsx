import SubscriptionForm from "@/components/Stripe/Subscription/SubscriptionForm";
import { UnsubscribeButton } from "@/components/Stripe/Subscription/UnsubscribeButton";
import { auth } from "@/server/auth";
import { findExistingSubscriptionByUserId } from "@/utils/stripe";

export default async function CheckoutPage() {
  const stripeKey = process.env.STRIPE_PUBLISHABLE_KEY!;
  const session = await auth();

  const userId = session?.user?.id;

  const existingSubscription = userId
    ? await findExistingSubscriptionByUserId(userId)
    : null;

  console.log("Existing subscription:", existingSubscription);
  return (
    <div className="pt-8">
      {existingSubscription?.status == "active" ? (
        <div>
          <h1 className="text-xl font-bold">Your Subscription</h1>
          <p>Status: {existingSubscription?.status}</p>
          <p>
            Start Date:{" "}
            {new Date(
              existingSubscription?.start_date * 1000,
            ).toLocaleDateString()}
          </p>
          <p>
            Next Billing Date:{" "}
            {new Date(
              existingSubscription.current_period_end * 1000,
            ).toLocaleDateString()}
          </p>
          <div className="pt-4">
            {userId && <UnsubscribeButton userId={userId} />}
          </div>
        </div>
      ) : (
        <SubscriptionForm stripeKey={stripeKey}></SubscriptionForm>
      )}
    </div>
  );
}
