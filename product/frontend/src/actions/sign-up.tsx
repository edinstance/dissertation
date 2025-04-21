"use server";

import cognitoClient from "@/lib/cognito";
import {
  AdminCreateUserCommand,
  AdminSetUserPasswordCommand,
  MessageActionType,
} from "@aws-sdk/client-cognito-identity-provider";
import Stripe from "stripe";

/**
 * Creates a new user in the Cognito user pool and Stripe customer.
 *
 * @param params - The parameters for creating a user.
 * @returns A promise that resolves to an object indicating the result of the user creation process.
 * @throws Throws an error if user creation fails.
 */
async function createUser({
  name,
  email,
  password,
}: {
  name: string;
  email: string;
  password: string;
}) {
  try {
    const createUserCommand = new AdminCreateUserCommand({
      UserPoolId: process.env.COGNITO_USER_POOL_ID!,
      Username: email,
      UserAttributes: [
        { Name: "email", Value: email },
        { Name: "name", Value: name },
        { Name: "email_verified", Value: "true" },
      ],
      MessageAction: MessageActionType.SUPPRESS,
    });

    const user = await cognitoClient.send(createUserCommand);
    const id = user.User?.Username;

    if (!id) {
      throw new Error("User ID is undefined");
    }

    const setPasswordCommand = new AdminSetUserPasswordCommand({
      UserPoolId: process.env.COGNITO_USER_POOL_ID!,
      Username: email,
      Password: password,
      Permanent: true,
    });

    await cognitoClient.send(setPasswordCommand);

    const stripe = new Stripe(process.env.STRIPE_SECRET_KEY as string);

    const stripeResult = await stripe.customers.create({
      email,
      name,
      metadata: {
        userId: id,
      },
    });

    return { success: true, id, customerId: stripeResult.id };
  } catch (error) {
    console.error("Error in fetch:", error);
    return {
      error: error instanceof Error ? error.message : "Error creating user",
    };
  }
}

export default createUser;
