import "next-auth";

declare module "next-auth" {
  interface User {
    cognitoTokens?: CognitoTokens;
  }
  interface Session {
    accessToken: string;
    user: {
      id: string;
    } & DefaultSession["user"];
  }
  interface CognitoTokens {
    accessToken?: string;
    refreshToken?: string;
    idToken?: string;
    accessTokenExpiresIn?: number;
    tokenType?: string;
  }
}
