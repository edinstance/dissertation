import {
  AuthFlowType,
  CognitoIdentityProviderClient,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { CognitoJwtVerifier } from "aws-jwt-verify/cognito-verifier";

type TokenUse = "id" | "access";

/**
 * Verifies an AWS Cognito token.
 *
 * @param token - The token to verify.
 * @param tokenUse - The type of token being verified (either "id" or "access").
 * @returns A promise that resolves to the decoded token payload if verification is successful.
 * @throws Throws an error if the token verification fails.
 */
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

/**
 * Refreshes an access token using a refresh token.
 *
 * @param refreshToken - The refresh token used to obtain a new access token.
 * @returns A promise that resolves to an object containing the new access token and its expiration time.
 * @throws Throws an error if the refresh token operation fails.
 */
export async function refreshAccessToken(refreshToken: string) {
  const cognitoClient = new CognitoIdentityProviderClient({
    region: "eu-west-2",
  });

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
