import stripe from "@/utils/stripe";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  try {
    const { customerId, amount, itemId } = await request.json();

    if (!customerId || !amount || !itemId) {
      return NextResponse.json(
        { error: "Missing information" },
        { status: 400 },
      );
    }

    const setupIntent = await stripe.setupIntents.create({
      customer: customerId,
      payment_method_types: ["card"],
      usage: "on_session",
      metadata: {
        item_id: itemId,
        bid_amount_gbp: (amount / 100).toFixed(2),
        reason: `Bid authorization for item ${itemId}`,
      },
      description: `Authorize card for bid on item ${itemId}`,
    });

    return NextResponse.json({
      success: true,
      clientSecret: setupIntent.client_secret,
    });
  } catch (error) {
    console.error(error);
    return NextResponse.json({ error: "An error occurred" }, { status: 500 });
  }
}
