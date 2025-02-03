import { CognitoIdentityProviderClient } from "@aws-sdk/client-cognito-identity-provider";

const cognitoClient = new CognitoIdentityProviderClient({
  region: "eu-west-2",
});

export default cognitoClient;
