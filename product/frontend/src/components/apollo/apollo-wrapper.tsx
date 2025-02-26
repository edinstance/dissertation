"use client";
import { ApolloClient, createHttpLink, InMemoryCache } from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { ReactNode } from "react";
import ApolloProvider from "./apollo-provider";

/**
 * Creates an Apollo Client instance with optional authentication headers.
 *
 * @param link - The GraphQL endpoint URL.
 * @param accessToken - Optional access token for authorization.
 * @param apiKey - Optional API key for authorization.
 * @returns The configured Apollo Client instance.
 */
function createApolloClient(
  link: string,
  accessToken?: string,
  apiKey?: string,
) {
  const httpLink = createHttpLink({ uri: link, credentials: "include" });

  // Conditionally add the headers to avoid empty values
  const authHeaders: { [key: string]: string } = {};

  if (accessToken) {
    authHeaders["Authorization"] = `Bearer ${accessToken}`;
  }

  if (apiKey) {
    authHeaders["x-api-key"] = apiKey;
  }

  const authLink = setContext((_, { headers }) => ({
    headers: {
      ...headers,
      ...authHeaders,
    },
  }));

  return new ApolloClient({
    link: authLink.concat(httpLink),
    connectToDevTools: true,
    cache: new InMemoryCache(),
  });
}

/**
 * ApolloWrapper component that provides the Apollo Client to its children.
 *
 * This component wraps its children with the ApolloProvider, allowing them
 * to access the Apollo Client for GraphQL operations.
 *
 * @param props - The props for the component.
 * @param children - The child components to render.
 * @param link - The GraphQL endpoint URL.
 * @param accessToken - Optional access token for authorization.
 * @param apiKey - Optional API key for authorization.
 * @returns The rendered ApolloWrapper component.
 */
export function ApolloWrapper({
  children,
  link,
  accessToken,
  apiKey,
}: {
  children: ReactNode;
  link: string;
  accessToken?: string;
  apiKey?: string;
}) {
  return (
    <ApolloProvider client={createApolloClient(link, accessToken, apiKey)}>
      {children}
    </ApolloProvider>
  );
}
