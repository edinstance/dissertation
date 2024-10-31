import {
  AuthFlowType,
  CognitoIdentityProviderClient,
  InitiateAuthCommand,
} from "@aws-sdk/client-cognito-identity-provider";
import { CognitoJwtVerifier } from "aws-jwt-verify/cognito-verifier";
import { CredentialsSignin, type NextAuthConfig } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

class CustomError extends CredentialsSignin {
  constructor(code: string) {
    super();
    this.code = code;
    this.message = code;
  }
}

const verifier = CognitoJwtVerifier.create({
  userPoolId: process.env.COGNITO_USER_POOL_ID as string,
  tokenUse: "id",
  clientId: process.env.COGNITO_CLIENT_ID,
});

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

        const command = new InitiateAuthCommand({
          AuthFlow: AuthFlowType.USER_PASSWORD_AUTH,
          AuthParameters: {
            USERNAME: credentials.email as string,
            PASSWORD: credentials.password as string,
          },
          ClientId: process.env.COGNITO_CLIENT_ID,
        });

        try {
          const response = await cognitoClient.send(command);
          const userDetails = await verifier.verify(response.AuthenticationResult?.IdToken as string);
          
          const user = {
            id: userDetails.sub,
            email: credentials.email as string,
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
  pages: {
    signIn: "/login",
  },
  secret: process.env.NEXTAUTH_SECRET,
} satisfies NextAuthConfig;
