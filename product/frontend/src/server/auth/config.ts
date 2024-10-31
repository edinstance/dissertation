import { refreshAccessToken, verifyAWSToken } from "@/utils/aws-jwt";
import {
  AuthFlowType,
  CognitoIdentityProviderClient,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { CredentialsSignin, type NextAuthConfig } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

class CustomError extends CredentialsSignin {
  constructor(code: string) {
    super();
    this.code = code;
    this.message = code;
  }
}

/**
 * Options for NextAuth.js used to configure adapters, providers, callbacks, etc.
 *
 * @see https://next-auth.js.org/configuration/options
 */
export const authConfig = {
  providers: [
    CredentialsProvider({
      // The name to display on the sign in form (e.g. 'Sign in with...')
      name: "Credentials",
      credentials: {
        email: {
          label: "Email",
          type: "email",
          placeholder: "jsmith@google.com",
        },
        password: { label: "Password", type: "password" },
      },
      async authorize(credentials) {
        if (!credentials) throw new CustomError("No credentials provided");

        const cognitoClient = new CognitoIdentityProviderClient({});

        const loginUserCommand = new InitiateAuthCommand({
          AuthFlow: AuthFlowType.USER_PASSWORD_AUTH,
          AuthParameters: {
            USERNAME: credentials.email as string,
            PASSWORD: credentials.password as string,
          },
          ClientId: process.env.COGNITO_CLIENT_ID,
        });

        try {
          const response = await cognitoClient.send(loginUserCommand);
          const userDetails = await verifyAWSToken(
            response.AuthenticationResult?.IdToken as string,
            "id",
          );

          const user = {
            id: userDetails.sub,
            email: credentials.email as string,
            cognitoTokens: {
              accessToken: response.AuthenticationResult?.AccessToken,
              refreshToken: response.AuthenticationResult?.RefreshToken,
              idToken: response.AuthenticationResult?.IdToken,
              accessTokenExpiresIn: response.AuthenticationResult?.ExpiresIn,
              tokenType: response.AuthenticationResult?.TokenType,
            },
          };

          if (!user) throw new CustomError("Invalid credentials");

          return user;
        } catch (error) {
          console.error(error);
          throw new CustomError("Invalid credentials");
        }
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.accessToken = user.cognitoTokens?.accessToken;
        token.refreshToken = user.cognitoTokens?.refreshToken;
        token.idToken = user.cognitoTokens?.idToken;
        token.accessTokenExpiresIn = user.cognitoTokens?.accessTokenExpiresIn;
        token.tokenType = user.cognitoTokens?.tokenType;
      }
      const isTokenExpired =
        Date.now() > (token.accessTokenExpiresIn as number) * 1000;

      if (isTokenExpired) {
        try {
          const newTokens = await refreshAccessToken(
            token.refreshToken as string,
          );
          token.accessToken = newTokens.accessToken;
          token.accessTokenExpiresIn = newTokens.accessTokenExpiresIn;
        } catch (error) {
          console.error("Token refresh failed:", error);
          throw new Error("Token refresh failed");
        }
      }

      return token;
    },
    async session({ session, token }) {
      // Add accessToken to the session
      if (token) {
        session.accessToken = token.accessToken as string;
      }
      return session;
    },
  },
  pages: {
    signIn: "/login",
  },
  secret: process.env.NEXTAUTH_SECRET,
} satisfies NextAuthConfig;
