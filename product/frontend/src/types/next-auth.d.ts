declare module "next-auth" {
  interface User {
    cognitoTokens?: cognitoTokens;
  }
  interface Session {
    accessToken: string;
  }
  interface cognitoTokens {
    accessToken?: string;
    refreshToken?: string;
    idToken?: string;
    accessTokenExpiresIn?: number;
    tokenType?: string;
  }
}
