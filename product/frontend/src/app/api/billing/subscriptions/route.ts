import stripe from "@/lib/stripe";
import { findCustomerByUserId, findExistingSubscription, getCustomerSubscriptions } from "@/utils/stripe";
import { NextRequest, NextResponse } from "next/server";
import Stripe from "stripe";

export async function POST(request: NextRequest) {
  try {
    const { userId } = await request.json();

    if (!userId) {
      return NextResponse.json({ error: "Missing userId" }, { status: 400 });
    }


    const customer = await findCustomerByUserId(userId);

    if (!customer){
        return NextResponse.json({ error: "Customer not found" }, { status: 404 });
    }

    // List and filter subscriptions
    const subscriptions = await getCustomerSubscriptions(customer.id);

    const existingSubscription = findExistingSubscription(subscriptions);
    
    if (existingSubscription) {
      const latest_invoice =
        existingSubscription.latest_invoice as Stripe.Invoice;
      const payment_intent =
        latest_invoice.payment_intent as Stripe.PaymentIntent;

      if (!latest_invoice || !payment_intent) {
        throw new Error("Missing latest invoice or payment intent");
      }

      console.log("Existing subscription found:", existingSubscription.id);
      console.log(
        "Existing payment intent client_secret:",
        payment_intent.client_secret,
      );

      if (
        payment_intent.status === "requires_action" ||
        payment_intent.status === "requires_payment_method"
      ) {
        return NextResponse.json({
          client_secret: payment_intent.client_secret,
        });
      } else if (payment_intent.status === "succeeded") {
        return NextResponse.json({
          message: "Subscription is already paid and active.",
        });
      } else {
        throw new Error("Unhandled payment intent status");
      }
    } else {
      // Create a new subscription
      const subscription = await stripe.subscriptions.create({
        customer: customer.id,
        items: [
          {
            price: "price_1QU8pCGlnq0aqIkWouea1rQk",
          },
        ],
        payment_behavior: "default_incomplete",
        payment_settings: { save_default_payment_method: "on_subscription" },
        expand: ["latest_invoice.payment_intent"],
      });

      const latest_invoice = subscription.latest_invoice as Stripe.Invoice;
      const payment_intent =
        latest_invoice.payment_intent as Stripe.PaymentIntent;

      if (!latest_invoice || !payment_intent) {
        throw new Error("Failed to create subscription or retrieve payment intent");
      }

      console.log("Subscription created:", subscription.id);
      console.log(
        "Payment intent client_secret:",
        payment_intent.client_secret,
      );

      return NextResponse.json({
        client_secret: payment_intent.client_secret,
      });
    }
  } catch (error: any) {
    console.error("Error handling subscription:", error);
    return NextResponse.json(
      { success: false, error: error.message },
      { status: error.statusCode || 500 },
    );
  }
}
