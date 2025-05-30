import stripe, { findExistingSubscriptionByCustomerId } from "@/utils/stripe";
import { NextRequest, NextResponse } from "next/server";
import Stripe from "stripe";

export async function POST(request: NextRequest) {
  try {
    const { customerId } = await request.json();

    if (!customerId) {
      return NextResponse.json(
        { error: "Missing customer id" },
        { status: 400 },
      );
    }

    const priceId = process.env.STRIPE_PRICE_ID;
    if (!priceId) {
      return NextResponse.json(
        { error: "Missing STRIPE_PRICE_ID" },
        { status: 500 },
      );
    }

    // Check for existing subscription
    const existingSubscription =
      await findExistingSubscriptionByCustomerId(customerId);

    if (existingSubscription) {
      // Handle specific statuses of the existing subscription
      const latestInvoice =
        existingSubscription.latest_invoice as Stripe.Invoice;
      const paymentIntent =
        latestInvoice?.payment_intent as Stripe.PaymentIntent;

      if (existingSubscription.status === "canceled") {
        // Reactivate the subscription and return client_secret
        const newSubscription = await stripe.subscriptions.create({
          customer: customerId,
          items: [{ price: priceId }],
          payment_behavior: "default_incomplete",
          payment_settings: { save_default_payment_method: "on_subscription" },
          expand: ["latest_invoice.payment_intent"],
        });

        const updatedInvoice = newSubscription.latest_invoice as Stripe.Invoice;
        const updatedPaymentIntent =
          updatedInvoice.payment_intent as Stripe.PaymentIntent;

        return NextResponse.json({
          client_secret: updatedPaymentIntent?.client_secret,
          subscription_id: newSubscription.id,
        });
      }

      if (existingSubscription.status === "incomplete") {
        // Return the client_secret to complete the subscription
        return NextResponse.json({
          client_secret: paymentIntent?.client_secret,
          subscription_id: existingSubscription.id,
        });
      }

      // If the subscription is already active
      return NextResponse.json({
        message: "Subscription is already active.",
        subscription_id: existingSubscription.id,
        status: existingSubscription.status,
      });
    }

    const newSubscription = await stripe.subscriptions.create({
      customer: customerId,
      items: [{ price: priceId }],
      payment_behavior: "default_incomplete",
      payment_settings: { save_default_payment_method: "on_subscription" },
      expand: ["latest_invoice.payment_intent"],
    });

    const latestInvoice = newSubscription.latest_invoice as Stripe.Invoice;
    const paymentIntent = latestInvoice.payment_intent as Stripe.PaymentIntent;

    return NextResponse.json({
      client_secret: paymentIntent?.client_secret,
      subscription_id: newSubscription.id,
    });
  } catch (error: unknown) {
    console.error("Error in subscription handler:", error);
    const errorMessage =
      error instanceof Error ? error.message : "Unexpected error occurred";
    return NextResponse.json({ error: errorMessage }, { status: 500 });
  }
}
