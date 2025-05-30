"use server";

import cognitoClient from "@/lib/cognito";
import { AdminAddUserToGroupCommand } from "@aws-sdk/client-cognito-identity-provider";

export async function addUserToAdminGroup({ id }: { id: string }) {
  // Ensure the Cognito User Pool ID is set
  const userPoolId = process.env.COGNITO_USER_POOL_ID;
  if (!userPoolId) {
    throw new Error("Cognito User Pool ID is not defined");
  }

  const addUserToGroupCommand = new AdminAddUserToGroupCommand({
    UserPoolId: userPoolId,
    Username: id,
    GroupName: "SubShopAdmin",
  });

  await cognitoClient.send(addUserToGroupCommand);
}
