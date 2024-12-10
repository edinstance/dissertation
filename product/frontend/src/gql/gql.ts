/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from "@graphql-typed-document-node/core";
import * as types from "./graphql";

/**
 * Map of all GraphQL operations in the project.
 *
 * This map has several performance disadvantages:
 * 1. It is not tree-shakeable, so it will include all operations in the project.
 * 2. It is not minifiable, so the string of a GraphQL query will be multiple times inside the bundle.
 * 3. It does not support dead code elimination, so it will add unused operations.
 *
 * Therefore it is highly recommended to use the babel or swc plugin for production.
 * Learn more about it here: https://the-guild.dev/graphql/codegen/plugins/presets/preset-client#reducing-bundle-size
 */
const documents = {
  "\n  mutation CreateUser($input: UserInput!) {\n    createUser(userInput: $input) {\n      id\n    }\n  }\n":
    types.CreateUserDocument,
  "\n  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {\n    saveUserDetails(id: $id, detailsInput: $detailsInput) {\n      id\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.SaveUserDetailsDocument,
  "\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.GetUserDocument,
};

/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 *
 *
 * @example
 * ```ts
 * const query = graphql(`query GetUser($id: ID!) { user(id: $id) { name } }`);
 * ```
 *
 * The query argument is unknown!
 * Please regenerate the types.
 */
export function graphql(source: string): unknown;

/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation CreateUser($input: UserInput!) {\n    createUser(userInput: $input) {\n      id\n    }\n  }\n",
): (typeof documents)["\n  mutation CreateUser($input: UserInput!) {\n    createUser(userInput: $input) {\n      id\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {\n    saveUserDetails(id: $id, detailsInput: $detailsInput) {\n      id\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n",
): (typeof documents)["\n  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {\n    saveUserDetails(id: $id, detailsInput: $detailsInput) {\n      id\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n"];

export function graphql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> =
  TDocumentNode extends DocumentNode<infer TType, any> ? TType : never;
