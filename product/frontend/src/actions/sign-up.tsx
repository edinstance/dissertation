"use server";

import {
  AdminCreateUserCommand,
  AdminSetUserPasswordCommand,
  CognitoIdentityProviderClient,
  MessageActionType,
} from "@aws-sdk/client-cognito-identity-provider";

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
    const cognitoClient = new CognitoIdentityProviderClient({});

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

    return { success: true, id };
  } catch (error) {
    console.error("Error in fetch:", error);
    return {
      error: error instanceof Error ? error.message : "Error creating user",
    };
  }
}

export default createUser;
