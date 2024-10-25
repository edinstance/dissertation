"use client";
import { ApolloClient, InMemoryCache } from "@apollo/client";
import { ReactNode } from "react";
import ApolloProvider from "./apollo-provider";

function createApolloClient(link: string) {
  return new ApolloClient({
    uri: link,
    connectToDevTools: true,
    cache: new InMemoryCache(),
  });
}

export function ApolloWrapper({
  children,
  link,
}: {
  children: ReactNode;
  link: string;
}) {
  return (
    <ApolloProvider client={createApolloClient(link)}>
      {children}
    </ApolloProvider>
  );
}
