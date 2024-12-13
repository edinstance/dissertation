import stripe from "@/lib/stripe";
import {
  findCustomerByUserId,
  findExistingSubscriptionByUserId,
} from "@/utils/stripe";
import { NextRequest, NextResponse } from "next/server";
import Stripe from "stripe";

export async function POST(request: NextRequest) {
  try {
    const { userId } = await request.json();

    if (!userId) {
      return NextResponse.json({ error: "Missing userId" }, { status: 400 });
    }

    const priceId = process.env.STRIPE_PRICE_ID;
    if (!priceId) {
      return NextResponse.json(
        { error: "Missing STRIPE_PRICE_ID" },
        { status: 500 },
      );
    }

    const customer = await findCustomerByUserId(userId);
    if (!customer) {
      return NextResponse.json(
        { error: "Customer not found" },
        { status: 404 },
      );
    }

    // Check for existing subscription
    const existingSubscription = await findExistingSubscriptionByUserId(userId);

    if (existingSubscription) {
      // Handle specific statuses of the existing subscription
      const latestInvoice =
        existingSubscription.latest_invoice as Stripe.Invoice;
      const paymentIntent =
        latestInvoice?.payment_intent as Stripe.PaymentIntent;

      if (existingSubscription.status === "canceled") {
        // Reactivate the subscription and return client_secret
        const newSubscription = await stripe.subscriptions.create({
          customer: customer.id,
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
        });
      }

      if (existingSubscription.status === "incomplete") {
        // Return the client_secret to complete the subscription
        return NextResponse.json({
          client_secret: paymentIntent?.client_secret,
        });
      }

      // If the subscription is already active
      return NextResponse.json({ message: "Subscription is already active." });
    }

    // No existing subscription, create a new one

    const newSubscription = await stripe.subscriptions.create({
      customer: customer.id,
      items: [{ price: priceId }],
      payment_behavior: "default_incomplete",
      payment_settings: { save_default_payment_method: "on_subscription" },
      expand: ["latest_invoice.payment_intent"],
    });

    const latestInvoice = newSubscription.latest_invoice as Stripe.Invoice;
    const paymentIntent = latestInvoice.payment_intent as Stripe.PaymentIntent;

    return NextResponse.json({
      client_secret: paymentIntent?.client_secret,
    });
  } catch (error) {
    console.error("Error in subscription handler:", error);
    return NextResponse.json(
      { error: error.message || "Unexpected error occurred" },
      { status: 500 },
    );
  }
}
