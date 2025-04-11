import "@/styles/globals.css";

import { ApolloWrapper } from "@/components/apollo";

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";
const BACKEND_GRAPHQL_WS_ENDPOINT =
  process.env.BACKEND_GRAPHQL_WS_ENDPOINT ?? "";
const BACKEND_API_KEY = process.env.API_KEY ?? "";

/**
 * RootLayout component for the signup page.
 *
 * This component serves as the main layout for the signup page, wrapping
 * the content with the ApolloWrapper to provide GraphQL functionality.
 * It passes the backend GraphQL endpoint and API key as props to the ApolloWrapper.
 *
 * @param props - The props for the component.
 * @returns The rendered RootLayout component.
 */
export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <ApolloWrapper
      httpLinkUri={BACKEND_GRAPHQL_ENDPOINT}
      wsLinkUri={BACKEND_GRAPHQL_WS_ENDPOINT}
      apiKey={BACKEND_API_KEY}
    >
      {children}
    </ApolloWrapper>
  );
}
