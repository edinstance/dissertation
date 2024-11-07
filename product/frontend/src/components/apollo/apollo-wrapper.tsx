"use client";
import { ApolloClient, createHttpLink, InMemoryCache } from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { ReactNode } from "react";
import ApolloProvider from "./apollo-provider";

function createApolloClient(link: string, accessToken: string) {
  const httpLink = createHttpLink({ uri: link, credentials: "include" });

  const authLink = setContext((_, { headers }) => ({
    headers: {
      ...headers,
      authorization: accessToken ? `Bearer ${accessToken}` : "",
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
}: {
  children: ReactNode;
  link: string;
  accessToken: string;
}) {
  return (
    <ApolloProvider client={createApolloClient(link, accessToken)}>
      {children}
    </ApolloProvider>
  );
}
