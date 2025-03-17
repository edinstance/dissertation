import "next-auth";

declare module "next-auth" {
  interface User {
    groups: string[];
    cognitoTokens?: CognitoTokens;
  }
  interface Session {
    accessToken: string;
    user: {
      id: string;
      groups: string[];
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
