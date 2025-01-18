"use server";

import cognitoClient from "@/lib/cognito";
import { auth } from "@/server/auth";
import { findCustomerByUserId } from "@/utils/stripe";
import { AdminDeleteUserCommand } from "@aws-sdk/client-cognito-identity-provider";
import Stripe from "stripe";

async function deleteUser() {
  const stripe = new Stripe(process.env.STRIPE_SECRET_KEY as string);

  try {
    const session = await auth();

    if (!session) {
      throw new Error("Not authenticated");
    }

    const userId = session.user.id;

    // Find the Stripe customer associated with the user
    const stripeCustomer = await findCustomerByUserId(userId);
    if (!stripeCustomer) {
      throw new Error("Stripe customer not found");
    }

    // Delete the Stripe customer
    await stripe.customers.del(stripeCustomer.id);

    // Ensure the Cognito User Pool ID is set
    const userPoolId = process.env.COGNITO_USER_POOL_ID;
    if (!userPoolId) {
      throw new Error("Cognito User Pool ID is not defined");
    }

    // Delete the user from Cognito
    const deleteUserCommand = new AdminDeleteUserCommand({
      UserPoolId: userPoolId,
      Username: session.user.email,
    });

    await cognitoClient.send(deleteUserCommand);

    return { success: true, message: `User ${userId} deleted successfully.` };
  } catch (error) {
    console.error("Error in deleteUser:", error);
    return {
      error: error instanceof Error ? error.message : "Error deleting user",
    };
  }
}

export default deleteUser;
