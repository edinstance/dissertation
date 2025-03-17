import cognitoClient from "@/lib/cognito";
import { refreshAccessToken, verifyAWSToken } from "@/utils/aws-jwt";
import {
  AuthFlowType,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { Session, User } from "next-auth";
import { JWT } from "next-auth/jwt";
import CredentialsProvider from "next-auth/providers/credentials";
import { z } from "zod";

/**
 * Custom error class for handling authentication errors.
 */
class CustomError {
  code: string;
  message: string;

  /**
   * Creates an instance of CustomError.
   * @param code - The error code.
   */
  constructor(code: string) {
    this.code = code;
    this.message = code;
  }
}

// Schema for validating user credentials
const credentialsSchema = z.object({
  email: z.string().email({ message: "Invalid email address" }),
  password: z
    .string()
    .min(8, { message: "Password must be at least 8 characters long" })
    .refine(
      (value) => /[A-Z]/.test(value) && /[a-z]/.test(value) && /\d/.test(value),
      { message: "Password must include uppercase, lowercase, and number" },
    ),
});

/**
 * Options for NextAuth.js used to configure adapters, providers, callbacks, etc.
 *
 * @see https://next-auth.js.org/configuration/options
 */
export const authConfig = {
  providers: [
    CredentialsProvider({
      // The name to display on the sign-in form (e.g. 'Sign in with...')
      name: "Credentials",
      credentials: {
        email: {
          label: "Email",
          type: "email",
          placeholder: "jsmith@google.com",
        },
        password: { label: "Password", type: "password" },
      },
      /**
       * Authorizes a user with the provided credentials.
       *
       * @param credentials - The credentials provided by the user.
       * @returns A promise that resolves to the user object if authorization is successful.
       * @throws Throws a CustomError if authorization fails.
       */
      async authorize(credentials) {
        if (!credentials) throw new CustomError("No credentials provided");

        const parsedCredentials = credentialsSchema.safeParse(credentials);
        if (!parsedCredentials.success) {
          throw new CustomError(
            parsedCredentials.error.errors.map((e) => e.message).join(", "),
          );
        }

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

          const accessDetails = await verifyAWSToken(
            response.AuthenticationResult?.AccessToken as string,
            "access",
          );
       

          const user = {
            id: userDetails.sub,
            email: credentials.email as string,
            name: userDetails.name as string,
            groups: accessDetails["cognito:groups"] || [],
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
    /**
     * JWT callback to manage token creation and refresh.
     *
     * @param param - The parameters for the callback.
     * @param token - The current JWT token.
     * @param user - The user object if available.
     * @returns A promise that resolves to the updated JWT token.
     */
    async jwt({ token, user }: { token: JWT; user?: User }) {
      if (user) {
        token.id = user.id;
        token.groups = user.groups;
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
    /**
     * Session callback to manage session data.
     *
     * @param param - The parameters for the callback.
     * @param session - The current session object.
     * @param token - The JWT token.
     * @returns A promise that resolves to the updated session object.
     */
    async session({ session, token }: { session: Session; token: JWT }) {
      // Add accessToken to the session
      if (token) {
        session.user.id = token.id as string;
        session.user.groups = token.groups as string[];
        session.accessToken = token.accessToken as string;
      }
      return session;
    },
  },
  pages: {
    signIn: "/sign-in",
  },
  trustHost: true,
  secret: process.env.AUTH_SECRET,
};
