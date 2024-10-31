import {
  AuthFlowType,
  CognitoIdentityProviderClient,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { CognitoJwtVerifier } from "aws-jwt-verify/cognito-verifier";

type TokenUse = "id" | "access";

export async function verifyAWSToken(token: string, tokenUse: TokenUse) {
  const verifier = CognitoJwtVerifier.create({
    userPoolId: process.env.COGNITO_USER_POOL_ID as string,
    tokenUse: tokenUse,
    clientId: process.env.COGNITO_CLIENT_ID as string,
  });

  try {
    const result = await verifier.verify(token);
    return result;
  } catch (error) {
    console.error("Token verification failed:", error);
    throw new Error("Invalid token");
  }
}

export async function refreshAccessToken(refreshToken: string) {
  const cognitoClient = new CognitoIdentityProviderClient({});

  const command = new InitiateAuthCommand({
    AuthFlow: AuthFlowType.REFRESH_TOKEN_AUTH,
    AuthParameters: {
      REFRESH_TOKEN: refreshToken,
    },
    ClientId: process.env.COGNITO_CLIENT_ID!,
  });

  try {
    const response = await cognitoClient.send(command);
    return {
      accessToken: response.AuthenticationResult?.AccessToken as string,
      accessTokenExpiresIn: response.AuthenticationResult?.ExpiresIn as number,
    };
  } catch (error) {
    console.error("Failed to refresh access token", error);
    throw new Error("Failed to refresh access token");
  }
}
