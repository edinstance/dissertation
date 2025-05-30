import { auth } from "@/server/auth";
import stripe from "@/utils/stripe";
import { NextResponse } from "next/server";

export async function POST(request: Request) {
  const session = await auth();
  const userId = session?.user?.id;

  let { accountId } = await request.json();

  if (accountId === null) {
    console.log(`Creating new Stripe Express account for user ${userId}`);
    try {
      const account = await stripe.accounts.create({
        type: "express",
        email: session?.user.email,
        capabilities: {
          card_payments: { requested: true },
          transfers: { requested: true },
        },
      });

      accountId = account.id;

      console.log(
        `Created Stripe account ${accountId}. Saving to user ${userId}`,
      );
    } catch (creationError) {
      console.error(
        `Failed to create new Stripe account for user ${userId}:`,
        creationError,
      );
      throw new Error(
        `Failed to create Stripe account: ${(creationError as Error).message}`,
      );
    }
  }

  if (!accountId) {
    console.error(
      `Failed to obtain a valid Stripe account ID for user ${userId}`,
    );
    return NextResponse.json(
      { error: { message: "Could not establish Stripe account ID." } },
      { status: 500 },
    );
  }

  try {
    const origin = request.headers.get("origin") || "http://localhost:3000";
    const accountLink = await stripe.accountLinks.create({
      account: accountId,
      refresh_url: `${origin}/account`,
      return_url: `${origin}/account`,
      type: "account_onboarding",
      collect: "eventually_due",
    });

    console.log(`Created account link for ${accountId}`);
    return NextResponse.json({ url: accountLink.url, accountId });
  } catch (linkError) {
    console.error(`Failed to create account link for ${accountId}:`, linkError);
    throw new Error(
      `Failed to create Stripe onboarding link: ${(linkError as Error).message}`,
    );
  }
}
