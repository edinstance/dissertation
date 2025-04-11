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
  "\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      id {\n        permissionId\n      }\n      resource\n      action\n    }\n  }\n":
    types.GetCurrentAdminPermissionsDocument,
  "\n  query getAdminPermissionsByAdminId($adminId: String!) {\n    getAdminPermissionsByAdminId(adminId: $adminId) {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n":
    types.GetAdminPermissionsByAdminIdDocument,
  "\n  mutation revokeAdminPermission($adminId: String!, $permissionId: String!) {\n    revokeAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n":
    types.RevokeAdminPermissionDocument,
  "\n  query getAllPermissions {\n    getAllPermissions {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n":
    types.GetAllPermissionsDocument,
  "\n  query getAllPermissionsNoDescriptions {\n    getAllPermissions {\n      id\n      resource {\n        id\n        resource\n      }\n      action {\n        actionId\n        action\n      }\n    }\n  }\n":
    types.GetAllPermissionsNoDescriptionsDocument,
  "\n  mutation createPermission($input: CreatePermissionInput!) {\n    createPermission(input: $input) {\n      success\n      message\n    }\n  }\n":
    types.CreatePermissionDocument,
  "\n  mutation grantAdminPermission($adminId: String!, $permissionId: String!) {\n    grantAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n":
    types.GrantAdminPermissionDocument,
  "\n  query getUserStats {\n    getUserStats {\n      total\n      newUserTotal\n      deletedUserTotal\n    }\n  }\n":
    types.GetUserStatsDocument,
  "\n  query getAllUsers {\n    getAllUsers {\n      id\n      email\n      name\n      status\n    }\n  }\n":
    types.GetAllUsersDocument,
  "\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n":
    types.GetCurrentAdminDocument,
  "\n  query getAllAdminIds {\n    getAllAdmins {\n      userId\n    }\n  }\n":
    types.GetAllAdminIdsDocument,
  "\n  query getAllAdmins {\n    getAllAdmins {\n      userId\n      email\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n":
    types.GetAllAdminsDocument,
  "\n  mutation CreateAdmin($userId: String!) {\n    createAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n":
    types.CreateAdminDocument,
  "\n  mutation DeactivateAdmin($userId: String!) {\n    deactivateAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n":
    types.DeactivateAdminDocument,
  "\n  mutation createChat($conversationId: String!, $message: String!) {\n    createChat(conversationId: $conversationId, message: $message) {\n      chat {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n      response {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n    }\n  }\n":
    types.CreateChatDocument,
  "\n  mutation clearCurrentConversation($conversationId: String!) {\n    clearCurrentConversation(conversationId: $conversationId) {\n      success\n      message\n    }\n  }\n":
    types.ClearCurrentConversationDocument,
  "\n  query getCurrentConversation($conversationId: String!) {\n    getCurrentConversation(conversationId: $conversationId) {\n      chatId\n      userId\n      sender\n      message\n      createdAt\n    }\n  }\n":
    types.GetCurrentConversationDocument,
  '\n  query GetActionsEnumValues {\n    Actions: __type(name: "Actions") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n':
    types.GetActionsEnumValuesDocument,
  '\n  query GetResoucesEnumValues {\n    Resources: __type(name: "Resources") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n':
    types.GetResoucesEnumValuesDocument,
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
  "\n  mutation ReportBug($title: String!, $description: String!) {\n    reportBug(title: $title, description: $description) {\n      success\n      message\n    }\n  }\n":
    types.ReportBugDocument,
  "\n  mutation CreateUser($input: UserInput!) {\n    createUser(userInput: $input) {\n      id\n    }\n  }\n":
    types.CreateUserDocument,
  "\n  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {\n    saveUserDetails(id: $id, detailsInput: $detailsInput) {\n      id\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.SaveUserDetailsDocument,
  "\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      status\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n":
    types.GetUserDocument,
  "\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n":
    types.DeleteUserDocument,
  "\n  mutation DeactivateUser($id: String!) {\n    deactivateUser(id: $id) {\n      success\n      message\n    }\n  }\n":
    types.DeactivateUserDocument,
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
  source: "\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      id {\n        permissionId\n      }\n      resource\n      action\n    }\n  }\n",
): (typeof documents)["\n  query getCurrentAdminPermissions {\n    getCurrentAdminPermissions {\n      id {\n        permissionId\n      }\n      resource\n      action\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAdminPermissionsByAdminId($adminId: String!) {\n    getAdminPermissionsByAdminId(adminId: $adminId) {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getAdminPermissionsByAdminId($adminId: String!) {\n    getAdminPermissionsByAdminId(adminId: $adminId) {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation revokeAdminPermission($adminId: String!, $permissionId: String!) {\n    revokeAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation revokeAdminPermission($adminId: String!, $permissionId: String!) {\n    revokeAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAllPermissions {\n    getAllPermissions {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getAllPermissions {\n    getAllPermissions {\n      id\n      description\n      resource {\n        id\n        resource\n        description\n      }\n      action {\n        actionId\n        action\n        description\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAllPermissionsNoDescriptions {\n    getAllPermissions {\n      id\n      resource {\n        id\n        resource\n      }\n      action {\n        actionId\n        action\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getAllPermissionsNoDescriptions {\n    getAllPermissions {\n      id\n      resource {\n        id\n        resource\n      }\n      action {\n        actionId\n        action\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation createPermission($input: CreatePermissionInput!) {\n    createPermission(input: $input) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation createPermission($input: CreatePermissionInput!) {\n    createPermission(input: $input) {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation grantAdminPermission($adminId: String!, $permissionId: String!) {\n    grantAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation grantAdminPermission($adminId: String!, $permissionId: String!) {\n    grantAdminPermission(adminId: $adminId, permissionId: $permissionId) {\n      success\n      message\n    }\n  }\n"];
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
  source: "\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n",
): (typeof documents)["\n  query getCurrentAdmin {\n    getCurrentAdmin {\n      userId\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAllAdminIds {\n    getAllAdmins {\n      userId\n    }\n  }\n",
): (typeof documents)["\n  query getAllAdminIds {\n    getAllAdmins {\n      userId\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getAllAdmins {\n    getAllAdmins {\n      userId\n      email\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n",
): (typeof documents)["\n  query getAllAdmins {\n    getAllAdmins {\n      userId\n      email\n      isSuperAdmin\n      status\n      isDeleted\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation CreateAdmin($userId: String!) {\n    createAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation CreateAdmin($userId: String!) {\n    createAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation DeactivateAdmin($userId: String!) {\n    deactivateAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation DeactivateAdmin($userId: String!) {\n    deactivateAdmin(userId: $userId) {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation createChat($conversationId: String!, $message: String!) {\n    createChat(conversationId: $conversationId, message: $message) {\n      chat {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n      response {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n    }\n  }\n",
): (typeof documents)["\n  mutation createChat($conversationId: String!, $message: String!) {\n    createChat(conversationId: $conversationId, message: $message) {\n      chat {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n      response {\n        chatId\n        userId\n        sender\n        message\n        createdAt\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation clearCurrentConversation($conversationId: String!) {\n    clearCurrentConversation(conversationId: $conversationId) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation clearCurrentConversation($conversationId: String!) {\n    clearCurrentConversation(conversationId: $conversationId) {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  query getCurrentConversation($conversationId: String!) {\n    getCurrentConversation(conversationId: $conversationId) {\n      chatId\n      userId\n      sender\n      message\n      createdAt\n    }\n  }\n",
): (typeof documents)["\n  query getCurrentConversation($conversationId: String!) {\n    getCurrentConversation(conversationId: $conversationId) {\n      chatId\n      userId\n      sender\n      message\n      createdAt\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: '\n  query GetActionsEnumValues {\n    Actions: __type(name: "Actions") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n',
): (typeof documents)['\n  query GetActionsEnumValues {\n    Actions: __type(name: "Actions") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n'];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: '\n  query GetResoucesEnumValues {\n    Resources: __type(name: "Resources") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n',
): (typeof documents)['\n  query GetResoucesEnumValues {\n    Resources: __type(name: "Resources") {\n      name\n      enumValues(includeDeprecated: false) {\n        name\n      }\n    }\n  }\n'];
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
  source: "\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      status\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n",
): (typeof documents)["\n  query getUser {\n    getUser {\n      id\n      email\n      name\n      status\n      details {\n        id\n        contactNumber\n        houseName\n        addressStreet\n        addressCity\n        addressCounty\n        addressPostcode\n      }\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation DeleteUser {\n    deleteUser {\n      success\n      message\n    }\n  }\n"];
/**
 * The graphql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function graphql(
  source: "\n  mutation DeactivateUser($id: String!) {\n    deactivateUser(id: $id) {\n      success\n      message\n    }\n  }\n",
): (typeof documents)["\n  mutation DeactivateUser($id: String!) {\n    deactivateUser(id: $id) {\n      success\n      message\n    }\n  }\n"];

export function graphql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> =
  TDocumentNode extends DocumentNode<infer TType, any> ? TType : never;
