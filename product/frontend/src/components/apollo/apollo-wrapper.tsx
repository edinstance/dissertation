"use client";

import {
  ApolloClient,
  createHttpLink,
  InMemoryCache,
  split,
} from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { GraphQLWsLink } from "@apollo/client/link/subscriptions";
import { getMainDefinition } from "@apollo/client/utilities";
import { createClient } from "graphql-ws";
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
  httpLinkUri: string,
  wsLinkUri: string,
  accessToken?: string,
  apiKey?: string,
) {
  const httpLink = createHttpLink({
    uri: httpLinkUri,
    credentials: "include",
  });

  const wsLink = new GraphQLWsLink(
    createClient({
      url: wsLinkUri,
      connectionParams: () => {
        const authHeaders: { [key: string]: string } = {};
        if (accessToken) {
          authHeaders["Authorization"] = `Bearer ${accessToken}`;
        }
        if (apiKey) {
          authHeaders["x-api-key"] = apiKey;
        }
        return authHeaders;
      },
    }),
  );

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
    link: split(
      ({ query }) => {
        const definition = getMainDefinition(query);
        return (
          definition.kind === "OperationDefinition" &&
          definition.operation === "subscription"
        );
      },
      wsLink,
      authLink.concat(httpLink),
    ),
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
  httpLinkUri,
  wsLinkUri,
  accessToken,
  apiKey,
}: {
  children: ReactNode;
  httpLinkUri: string;
  wsLinkUri: string;
  accessToken?: string;
  apiKey?: string;
}) {
  return (
    <ApolloProvider
      client={createApolloClient(httpLinkUri, wsLinkUri, accessToken, apiKey)}
    >
      {children}
    </ApolloProvider>
  );
}

// /**
//  * Creates an Apollo Client instance with optional authentication headers and
//  * WebSocket support for subscriptions.
//  */
// function createApolloClient(
//   httpLinkUri: string,
//   wsLinkUri: string,
//   accessToken?: string,
//   apiKey?: string,
// ) {
//   const httpLink = createHttpLink({
//     uri: httpLinkUri,
//     credentials: "include",
//   });

//   const wsLink = new GraphQLWsLink(
//     createClient({
//       url: wsLinkUri,
//       connectionParams: () => {
//         const authHeaders: { [key: string]: string } = {};
//         if (accessToken) {
//           authHeaders["Authorization"] = `Bearer ${accessToken}`;
//         }
//         if (apiKey) {
//           authHeaders["x-api-key"] = apiKey;
//         }
//         return authHeaders;
//       },
//     }),
//   );

//   const authLink = setContext((_, { headers }) => {
//     return {
//       headers: {
//         ...headers,
//       },
//     };
//   });
