import { auth } from "@/server/auth";
import stripe from "@/utils/stripe";
import { NextResponse } from "next/server";

export async function GET(request: Request) {
  const session = await auth();
  const userId = session?.user?.id;

  const url = new URL(request.url);
  const accountId = url.searchParams.get("accountId");

  if (!accountId || accountId === null) {
    return NextResponse.json(
      { error: { message: "Stripe account ID is required" } },
      { status: 400 },
    );
  }

  if (!userId) {
    console.error(`User ID is missing for account ${accountId}`);
    return NextResponse.json(
      { error: { message: "User ID is required" } },
      { status: 400 },
    );
  }

  try {
    const loginLink = await stripe.accounts.createLoginLink(accountId);

    console.log(`Created dashboard login link for ${accountId}`);
    return NextResponse.json({ url: loginLink.url });
  } catch (error) {
    console.error(`Failed to create dashboard link for ${accountId}:`, error);
    return NextResponse.json(
      {
        error: {
          message: `Failed to create dashboard link: ${(error as Error).message}`,
        },
      },
      { status: 500 },
    );
  }
}
