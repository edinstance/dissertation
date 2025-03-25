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
  "\n  query getUserStats {\n    getUserStats {\n      total\n      newUserTotal\n      deletedUserTotal\n    }\n  }\n":
    types.GetUserStatsDocument,
  "\n  query getAllUsers {\n    getAllUsers {\n      id\n      email\n      name\n      status\n    }\n  }\n":
    types.GetAllUsersDocument,
  "\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      createdBy\n      lastUpdatedBy\n      isDeleted\n    }\n  }\n":
    types.GetCurrentAdminDocument,
  "\n  query SearchItems(\n    $searchText: String\n    $pagination: PaginationInput\n    $sorting: SortInput\n  ) {\n    searchForItems(\n      searchText: $searchText\n      pagination: $pagination\n      sorting: $sorting\n    ) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n":
    types.SearchItemsDocument,
  "\n  query getShopItems($pagination: PaginationInput, $sorting: SortInput) {\n    getShopItems(pagination: $pagination, sorting: $sorting) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n":
    types.GetShopItemsDocument,
  "\n  query GetItemById($id: String!) {\n    getItemById(id: $id) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n":
    types.GetItemByIdDocument,
  "\n  query GetItemsByUser(\n    $id: String!\n    $isActive: Boolean\n    $pagination: PaginationInput\n  ) {\n    getItemsByUser(id: $id, isActive: $isActive, pagination: $pagination) {\n      items {\n        id\n        name\n        description\n        isActive\n        stock\n      }\n      pagination {\n        total\n        page\n        size\n      }\n    }\n  }\n":
    types.GetItemsByUserDocument,
  "\n  mutation SaveItem($itemInput: ItemInput!) {\n    saveItem(itemInput: $itemInput) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n":
    types.SaveItemDocument,
  "\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      grantType\n      resource\n      action\n    }\n  }\n":
    types.GetCurrentAdminPermissionsDocument,
  "\n  mutation ReportBug($title: String!, $description: String!) {\n    reportBug(title: $title, description: $description) {\n      success\n      message\n    }\n  }\n":
    types.ReportBugDocument,
  "\n  mutation CreateUser($input: UserInput!) {\n    createUser(userInput: $input) {\n      id\n    }\n  }\n":
    types.CreateUserDocument,
  "\n  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {\n    saveUserDetails(id: $id, detailsInput: $detailsInput) {\n      id\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.SaveUserDetailsDocument,
  "\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.GetUserDocument,
  "\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n":
    types.DeleteUserDocument,
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
  source: "\n  query getUserStats {\n    getUserStats {\n      total\n      newUserTotal\n      deletedUserTotal\n    }\n  }\n",
): (typeof documents)["\n  query getUserStats {\n    getUserStats {\n      total\n      newUserTotal\n      deletedUserTotal\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAllUsers {\n    getAllUsers {\n      id\n      email\n      name\n      status\n    }\n  }\n",
): (typeof documents)["\n  query getAllUsers {\n    getAllUsers {\n      id\n      email\n      name\n      status\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      createdBy\n      lastUpdatedBy\n      isDeleted\n    }\n  }\n",
): (typeof documents)["\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      createdBy\n      lastUpdatedBy\n      isDeleted\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query SearchItems(\n    $searchText: String\n    $pagination: PaginationInput\n    $sorting: SortInput\n  ) {\n    searchForItems(\n      searchText: $searchText\n      pagination: $pagination\n      sorting: $sorting\n    ) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n",
): (typeof documents)["\n  query SearchItems(\n    $searchText: String\n    $pagination: PaginationInput\n    $sorting: SortInput\n  ) {\n    searchForItems(\n      searchText: $searchText\n      pagination: $pagination\n      sorting: $sorting\n    ) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getShopItems($pagination: PaginationInput, $sorting: SortInput) {\n    getShopItems(pagination: $pagination, sorting: $sorting) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getShopItems($pagination: PaginationInput, $sorting: SortInput) {\n    getShopItems(pagination: $pagination, sorting: $sorting) {\n      items {\n        id\n        name\n        description\n        isActive\n        endingTime\n        price\n        stock\n        category\n        images\n        seller {\n          id\n          name\n          email\n        }\n      }\n      pagination {\n        total\n        page\n        size\n      }\n      sorting {\n        sortBy\n        sortDirection\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query GetItemById($id: String!) {\n    getItemById(id: $id) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n",
): (typeof documents)["\n  query GetItemById($id: String!) {\n    getItemById(id: $id) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query GetItemsByUser(\n    $id: String!\n    $isActive: Boolean\n    $pagination: PaginationInput\n  ) {\n    getItemsByUser(id: $id, isActive: $isActive, pagination: $pagination) {\n      items {\n        id\n        name\n        description\n        isActive\n        stock\n      }\n      pagination {\n        total\n        page\n        size\n      }\n    }\n  }\n",
): (typeof documents)["\n  query GetItemsByUser(\n    $id: String!\n    $isActive: Boolean\n    $pagination: PaginationInput\n  ) {\n    getItemsByUser(id: $id, isActive: $isActive, pagination: $pagination) {\n      items {\n        id\n        name\n        description\n        isActive\n        stock\n      }\n      pagination {\n        total\n        page\n        size\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation SaveItem($itemInput: ItemInput!) {\n    saveItem(itemInput: $itemInput) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n",
): (typeof documents)["\n  mutation SaveItem($itemInput: ItemInput!) {\n    saveItem(itemInput: $itemInput) {\n      id\n      name\n      description\n      isActive\n      endingTime\n      price\n      stock\n      category\n      images\n      seller {\n        id\n        name\n        email\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      grantType\n      resource\n      action\n    }\n  }\n",
): (typeof documents)["\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      grantType\n      resource\n      action\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation ReportBug($title: String!, $description: String!) {\n    reportBug(title: $title, description: $description) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation ReportBug($title: String!, $description: String!) {\n    reportBug(title: $title, description: $description) {\n      success\n      message\n    }\n  }\n"];
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
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n"];

export function graphql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> =
  TDocumentNode extends DocumentNode<infer TType, any> ? TType : never;
