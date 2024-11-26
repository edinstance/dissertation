"use client";
import { ApolloClient, createHttpLink, InMemoryCache } from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { ReactNode } from "react";
import ApolloProvider from "./apollo-provider";

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
