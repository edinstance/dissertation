/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from "@graphql-typed-document-node/core";
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = {
  [K in keyof T]: T[K];
};
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]?: Maybe<T[SubKey]>;
};
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & {
  [SubKey in K]: Maybe<T[SubKey]>;
};
export type MakeEmpty<
  T extends { [key: string]: unknown },
  K extends keyof T,
> = { [_ in K]?: never };
export type Incremental<T> =
  | T
  | {
      [P in keyof T]?: P extends " $fragmentName" | "__typename" ? T[P] : never;
    };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string };
  String: { input: string; output: string };
  Boolean: { input: boolean; output: boolean };
  Int: { input: number; output: number };
  Float: { input: number; output: number };
  _FieldSet: { input: any; output: any };
};

export type Action = {
  __typename?: "Action";
  action?: Maybe<Actions>;
  actionId: Scalars["String"]["output"];
  description?: Maybe<Scalars["String"]["output"]>;
};

export enum Actions {
  Create = "CREATE",
  Delete = "DELETE",
  Read = "READ",
  Write = "WRITE",
}

export type Admin = {
  __typename?: "Admin";
  email?: Maybe<Scalars["String"]["output"]>;
  isDeleted?: Maybe<Scalars["Boolean"]["output"]>;
  isSuperAdmin?: Maybe<Scalars["Boolean"]["output"]>;
  status?: Maybe<Scalars["String"]["output"]>;
  userId: Scalars["String"]["output"];
};

export type Chat = {
  __typename?: "Chat";
  chatId: Scalars["String"]["output"];
  conversationId: Scalars["String"]["output"];
  createdAt?: Maybe<Scalars["String"]["output"]>;
  message: Scalars["String"]["output"];
  sender: Scalars["String"]["output"];
  userId?: Maybe<Scalars["String"]["output"]>;
};

export type CreateChatResponse = {
  __typename?: "CreateChatResponse";
  chat?: Maybe<Chat>;
  response?: Maybe<Chat>;
};

export type CreatePermissionInput = {
  action: Actions;
  actionDescription?: InputMaybe<Scalars["String"]["input"]>;
  permissionDescription: Scalars["String"]["input"];
  resource: Resources;
  resourceDescription?: InputMaybe<Scalars["String"]["input"]>;
};

export enum ErrorDetail {
  /**
   * The deadline expired before the operation could complete.
   *
   * For operations that change the state of the system, this error
   * may be returned even if the operation has completed successfully.
   * For example, a successful response from a server could have been
   * delayed long enough for the deadline to expire.
   *
   * HTTP Mapping: 504 Gateway Timeout
   * Error Type: UNAVAILABLE
   */
  DeadlineExceeded = "DEADLINE_EXCEEDED",
  /**
   * The server detected that the client is exhibiting a behavior that
   * might be generating excessive load.
   *
   * HTTP Mapping: 429 Too Many Requests or 420 Enhance Your Calm
   * Error Type: UNAVAILABLE
   */
  EnhanceYourCalm = "ENHANCE_YOUR_CALM",
  /**
   * The requested field is not found in the schema.
   *
   * This differs from `NOT_FOUND` in that `NOT_FOUND` should be used when a
   * query is valid, but is unable to return a result (if, for example, a
   * specific video id doesn't exist). `FIELD_NOT_FOUND` is intended to be
   * returned by the server to signify that the requested field is not known to exist.
   * This may be returned in lieu of failing the entire query.
   * See also `PERMISSION_DENIED` for cases where the
   * requested field is invalid only for the given user or class of users.
   *
   * HTTP Mapping: 404 Not Found
   * Error Type: BAD_REQUEST
   */
  FieldNotFound = "FIELD_NOT_FOUND",
  /**
   * The client specified an invalid argument.
   *
   * Note that this differs from `FAILED_PRECONDITION`.
   * `INVALID_ARGUMENT` indicates arguments that are problematic
   * regardless of the state of the system (e.g., a malformed file name).
   *
   * HTTP Mapping: 400 Bad Request
   * Error Type: BAD_REQUEST
   */
  InvalidArgument = "INVALID_ARGUMENT",
  /**
   * The provided cursor is not valid.
   *
   * The most common usage for this error is when a client is paginating
   * through a list that uses stateful cursors. In that case, the provided
   * cursor may be expired.
   *
   * HTTP Mapping: 404 Not Found
   * Error Type: NOT_FOUND
   */
  InvalidCursor = "INVALID_CURSOR",
  /**
   * Unable to perform operation because a required resource is missing.
   *
   * Example: Client is attempting to refresh a list, but the specified
   * list is expired. This requires an action by the client to get a new list.
   *
   * If the user is simply trying GET a resource that is not found,
   * use the NOT_FOUND error type. FAILED_PRECONDITION.MISSING_RESOURCE
   * is to be used particularly when the user is performing an operation
   * that requires a particular resource to exist.
   *
   * HTTP Mapping: 400 Bad Request or 500 Internal Server Error
   * Error Type: FAILED_PRECONDITION
   */
  MissingResource = "MISSING_RESOURCE",
  /**
   * Service Error.
   *
   * There is a problem with an upstream service.
   *
   * This may be returned if a gateway receives an unknown error from a service
   * or if a service is unreachable.
   * If a request times out which waiting on a response from a service,
   * `DEADLINE_EXCEEDED` may be returned instead.
   * If a service returns a more specific error Type, the specific error Type may
   * be returned instead.
   *
   * HTTP Mapping: 502 Bad Gateway
   * Error Type: UNAVAILABLE
   */
  ServiceError = "SERVICE_ERROR",
  /**
   * Request failed due to network errors.
   *
   * HTTP Mapping: 503 Unavailable
   * Error Type: UNAVAILABLE
   */
  TcpFailure = "TCP_FAILURE",
  /**
   * Request throttled based on server concurrency limits.
   *
   * HTTP Mapping: 503 Unavailable
   * Error Type: UNAVAILABLE
   */
  ThrottledConcurrency = "THROTTLED_CONCURRENCY",
  /**
   * Request throttled based on server CPU limits
   *
   * HTTP Mapping: 503 Unavailable.
   * Error Type: UNAVAILABLE
   */
  ThrottledCpu = "THROTTLED_CPU",
  /**
   * The operation is not implemented or is not currently supported/enabled.
   *
   * HTTP Mapping: 501 Not Implemented
   * Error Type: BAD_REQUEST
   */
  Unimplemented = "UNIMPLEMENTED",
  /**
   * Unknown error.
   *
   * This error should only be returned when no other error detail applies.
   * If a client sees an unknown errorDetail, it will be interpreted as UNKNOWN.
   *
   * HTTP Mapping: 500 Internal Server Error
   */
  Unknown = "UNKNOWN",
}

export enum ErrorType {
  /**
   * Bad Request.
   *
   * There is a problem with the request.
   * Retrying the same request is not likely to succeed.
   * An example would be a query or argument that cannot be deserialized.
   *
   * HTTP Mapping: 400 Bad Request
   */
  BadRequest = "BAD_REQUEST",
  /**
   * The operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, the directory
   * to be deleted is non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   *
   * Service implementers can use the following guidelines to decide
   * between `FAILED_PRECONDITION` and `UNAVAILABLE`:
   *
   * - Use `UNAVAILABLE` if the client can retry just the failing call.
   * - Use `FAILED_PRECONDITION` if the client should not retry until
   * the system state has been explicitly fixed.  E.g., if an "rmdir"
   *      fails because the directory is non-empty, `FAILED_PRECONDITION`
   * should be returned since the client should not retry unless
   * the files are deleted from the directory.
   *
   * HTTP Mapping: 400 Bad Request or 500 Internal Server Error
   */
  FailedPrecondition = "FAILED_PRECONDITION",
  /**
   * Internal error.
   *
   * An unexpected internal error was encountered. This means that some
   * invariants expected by the underlying system have been broken.
   * This error code is reserved for serious errors.
   *
   * HTTP Mapping: 500 Internal Server Error
   */
  Internal = "INTERNAL",
  /**
   * The requested entity was not found.
   *
   * This could apply to a resource that has never existed (e.g. bad resource id),
   * or a resource that no longer exists (e.g. cache expired.)
   *
   * Note to server developers: if a request is denied for an entire class
   * of users, such as gradual feature rollout or undocumented allowlist,
   * `NOT_FOUND` may be used. If a request is denied for some users within
   * a class of users, such as user-based access control, `PERMISSION_DENIED`
   * must be used.
   *
   * HTTP Mapping: 404 Not Found
   */
  NotFound = "NOT_FOUND",
  /**
   * The caller does not have permission to execute the specified
   * operation.
   *
   * `PERMISSION_DENIED` must not be used for rejections
   * caused by exhausting some resource or quota.
   * `PERMISSION_DENIED` must not be used if the caller
   * cannot be identified (use `UNAUTHENTICATED`
   * instead for those errors).
   *
   * This error Type does not imply the
   * request is valid or the requested entity exists or satisfies
   * other pre-conditions.
   *
   * HTTP Mapping: 403 Forbidden
   */
  PermissionDenied = "PERMISSION_DENIED",
  /**
   * The request does not have valid authentication credentials.
   *
   * This is intended to be returned only for routes that require
   * authentication.
   *
   * HTTP Mapping: 401 Unauthorized
   */
  Unauthenticated = "UNAUTHENTICATED",
  /**
   * Currently Unavailable.
   *
   * The service is currently unavailable.  This is most likely a
   * transient condition, which can be corrected by retrying with
   * a backoff.
   *
   * HTTP Mapping: 503 Unavailable
   */
  Unavailable = "UNAVAILABLE",
  /**
   * Unknown error.
   *
   * For example, this error may be returned when
   * an error code received from another address space belongs to
   * an error space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   *
   * If a client sees an unknown errorType, it will be interpreted as UNKNOWN.
   * Unknown errors MUST NOT trigger any special behavior. These MAY be treated
   * by an implementation as being equivalent to INTERNAL.
   *
   * When possible, a more specific error should be provided.
   *
   * HTTP Mapping: 520 Unknown Error
   */
  Unknown = "UNKNOWN",
}

export enum GrantType {
  Deny = "DENY",
  Grant = "GRANT",
}

export type Item = {
  __typename?: "Item";
  category?: Maybe<Scalars["String"]["output"]>;
  description?: Maybe<Scalars["String"]["output"]>;
  endingTime?: Maybe<Scalars["String"]["output"]>;
  id?: Maybe<Scalars["String"]["output"]>;
  images?: Maybe<Array<Maybe<Scalars["String"]["output"]>>>;
  isActive?: Maybe<Scalars["Boolean"]["output"]>;
  name?: Maybe<Scalars["String"]["output"]>;
  price?: Maybe<Scalars["Float"]["output"]>;
  seller?: Maybe<User>;
  stock?: Maybe<Scalars["Int"]["output"]>;
};

export type ItemInput = {
  category?: InputMaybe<Scalars["String"]["input"]>;
  description?: InputMaybe<Scalars["String"]["input"]>;
  endingTime?: InputMaybe<Scalars["String"]["input"]>;
  id?: InputMaybe<Scalars["String"]["input"]>;
  images?: InputMaybe<Array<InputMaybe<Scalars["String"]["input"]>>>;
  isActive?: InputMaybe<Scalars["Boolean"]["input"]>;
  name?: InputMaybe<Scalars["String"]["input"]>;
  price?: InputMaybe<Scalars["Float"]["input"]>;
  stock?: InputMaybe<Scalars["Int"]["input"]>;
};

export enum ItemSortOptions {
  EndingTime = "ENDING_TIME",
  Price = "PRICE",
  Stock = "STOCK",
}

export type Mutation = {
  __typename?: "Mutation";
  clearCurrentConversation?: Maybe<MutationResponse>;
  createAdmin?: Maybe<MutationResponse>;
  createChat?: Maybe<CreateChatResponse>;
  createPermission?: Maybe<MutationResponse>;
  createUser?: Maybe<User>;
  deactivateAdmin?: Maybe<MutationResponse>;
  deactivateUser?: Maybe<MutationResponse>;
  deleteUser?: Maybe<MutationResponse>;
  grantAdminPermission?: Maybe<MutationResponse>;
  promoteAdminToSuperAdmin?: Maybe<MutationResponse>;
  reportBug?: Maybe<MutationResponse>;
  revokeAdminPermission?: Maybe<MutationResponse>;
  saveItem?: Maybe<Item>;
  saveUserDetails?: Maybe<User>;
};

export type MutationClearCurrentConversationArgs = {
  conversationId: Scalars["String"]["input"];
};

export type MutationCreateAdminArgs = {
  userId: Scalars["String"]["input"];
};

export type MutationCreateChatArgs = {
  conversationId: Scalars["String"]["input"];
  message: Scalars["String"]["input"];
};

export type MutationCreatePermissionArgs = {
  input: CreatePermissionInput;
};

export type MutationCreateUserArgs = {
  userInput?: InputMaybe<UserInput>;
};

export type MutationDeactivateAdminArgs = {
  userId: Scalars["String"]["input"];
};

export type MutationDeactivateUserArgs = {
  id: Scalars["String"]["input"];
};

export type MutationGrantAdminPermissionArgs = {
  adminId: Scalars["String"]["input"];
  permissionId: Scalars["String"]["input"];
};

export type MutationPromoteAdminToSuperAdminArgs = {
  userId: Scalars["String"]["input"];
};

export type MutationReportBugArgs = {
  description: Scalars["String"]["input"];
  title: Scalars["String"]["input"];
};

export type MutationRevokeAdminPermissionArgs = {
  adminId: Scalars["String"]["input"];
  permissionId: Scalars["String"]["input"];
};

export type MutationSaveItemArgs = {
  itemInput?: InputMaybe<ItemInput>;
};

export type MutationSaveUserDetailsArgs = {
  detailsInput?: InputMaybe<UserDetailsInput>;
  id: Scalars["String"]["input"];
};

export type MutationResponse = {
  __typename?: "MutationResponse";
  message?: Maybe<Scalars["String"]["output"]>;
  success?: Maybe<Scalars["Boolean"]["output"]>;
};

export type Pagination = {
  __typename?: "Pagination";
  page?: Maybe<Scalars["Int"]["output"]>;
  size?: Maybe<Scalars["Int"]["output"]>;
  total?: Maybe<Scalars["Int"]["output"]>;
};

export type PaginationInput = {
  page?: InputMaybe<Scalars["Int"]["input"]>;
  size?: InputMaybe<Scalars["Int"]["input"]>;
};

export type Permission = {
  __typename?: "Permission";
  action?: Maybe<Action>;
  description?: Maybe<Scalars["String"]["output"]>;
  id: Scalars["String"]["output"];
  resource?: Maybe<Resource>;
};

export type PermissionView = {
  __typename?: "PermissionView";
  action: Actions;
  grantType: GrantType;
  id: PermissionViewId;
  resource: Resources;
};

export type PermissionViewId = {
  __typename?: "PermissionViewId";
  actionId: Scalars["String"]["output"];
  permissionId: Scalars["String"]["output"];
  resourceId: Scalars["String"]["output"];
  userId: Scalars["String"]["output"];
};

export type Query = {
  __typename?: "Query";
  _service: _Service;
  getAdminPermissionsByAdminId?: Maybe<Array<Maybe<Permission>>>;
  getAllAdminPermissions?: Maybe<Array<Maybe<PermissionView>>>;
  getAllAdmins?: Maybe<Array<Maybe<Admin>>>;
  getAllPermissions?: Maybe<Array<Maybe<Permission>>>;
  getAllUsers?: Maybe<Array<Maybe<User>>>;
  getCurrentAdmin?: Maybe<Admin>;
  getCurrentAdminPermissions?: Maybe<Array<Maybe<PermissionView>>>;
  getCurrentConversation?: Maybe<Array<Maybe<Chat>>>;
  getItemById?: Maybe<Item>;
  getItemsByUser?: Maybe<SearchedItemsResponse>;
  getShopItems?: Maybe<SearchedItemsResponse>;
  getUser?: Maybe<User>;
  getUserStats?: Maybe<UserStats>;
  searchForItems?: Maybe<SearchedItemsResponse>;
};

export type QueryGetAdminPermissionsByAdminIdArgs = {
  adminId: Scalars["String"]["input"];
};

export type QueryGetCurrentConversationArgs = {
  conversationId: Scalars["String"]["input"];
};

export type QueryGetItemByIdArgs = {
  id: Scalars["String"]["input"];
};

export type QueryGetItemsByUserArgs = {
  id: Scalars["String"]["input"];
  isActive?: InputMaybe<Scalars["Boolean"]["input"]>;
  pagination?: InputMaybe<PaginationInput>;
};

export type QueryGetShopItemsArgs = {
  pagination?: InputMaybe<PaginationInput>;
  sorting?: InputMaybe<SortInput>;
};

export type QuerySearchForItemsArgs = {
  pagination?: InputMaybe<PaginationInput>;
  searchText?: InputMaybe<Scalars["String"]["input"]>;
  sorting?: InputMaybe<SortInput>;
};

export type Resource = {
  __typename?: "Resource";
  description?: Maybe<Scalars["String"]["output"]>;
  id: Scalars["String"]["output"];
  resource?: Maybe<Resources>;
};

export enum Resources {
  Admins = "ADMINS",
  AdminPermissions = "ADMIN_PERMISSIONS",
  AdminRoles = "ADMIN_ROLES",
  Permissions = "PERMISSIONS",
  Roles = "ROLES",
  Users = "USERS",
}

export type SearchedItemsResponse = {
  __typename?: "SearchedItemsResponse";
  items?: Maybe<Array<Maybe<Item>>>;
  pagination?: Maybe<Pagination>;
  sorting?: Maybe<Sorting>;
};

export enum SortDirection {
  Asc = "ASC",
  Desc = "DESC",
}

export type SortInput = {
  sortBy?: InputMaybe<Scalars["String"]["input"]>;
  sortDirection?: InputMaybe<SortDirection>;
};

export type Sorting = {
  __typename?: "Sorting";
  sortBy?: Maybe<Scalars["String"]["output"]>;
  sortDirection?: Maybe<SortDirection>;
};

export type Subscription = {
  __typename?: "Subscription";
  chatSubscription?: Maybe<Chat>;
};

export type SubscriptionChatSubscriptionArgs = {
  conversationId: Scalars["String"]["input"];
};

export type User = {
  __typename?: "User";
  details?: Maybe<UserDetails>;
  email: Scalars["String"]["output"];
  id: Scalars["String"]["output"];
  items?: Maybe<Array<Maybe<Item>>>;
  name?: Maybe<Scalars["String"]["output"]>;
  status?: Maybe<Scalars["String"]["output"]>;
};

export type UserDetails = {
  __typename?: "UserDetails";
  addressCity?: Maybe<Scalars["String"]["output"]>;
  addressCounty?: Maybe<Scalars["String"]["output"]>;
  addressPostcode?: Maybe<Scalars["String"]["output"]>;
  addressStreet?: Maybe<Scalars["String"]["output"]>;
  contactNumber?: Maybe<Scalars["String"]["output"]>;
  houseName?: Maybe<Scalars["String"]["output"]>;
  id: Scalars["String"]["output"];
};

export type UserDetailsInput = {
  addressCity?: InputMaybe<Scalars["String"]["input"]>;
  addressCounty?: InputMaybe<Scalars["String"]["input"]>;
  addressPostcode?: InputMaybe<Scalars["String"]["input"]>;
  addressStreet?: InputMaybe<Scalars["String"]["input"]>;
  contactNumber?: InputMaybe<Scalars["String"]["input"]>;
  houseName?: InputMaybe<Scalars["String"]["input"]>;
};

export type UserInput = {
  email: Scalars["String"]["input"];
  id: Scalars["String"]["input"];
  name?: InputMaybe<Scalars["String"]["input"]>;
};

export type UserStats = {
  __typename?: "UserStats";
  deletedUserTotal?: Maybe<Scalars["Int"]["output"]>;
  newUserTotal?: Maybe<Scalars["Int"]["output"]>;
  total?: Maybe<Scalars["Int"]["output"]>;
};

export type _Service = {
  __typename?: "_Service";
  sdl: Scalars["String"]["output"];
};

/**
 * The fundamental unit of any GraphQL Schema is the type. There are many kinds of types in GraphQL as represented by the `__TypeKind` enum.
 *
 * Depending on the kind of a type, certain fields describe information about that type. Scalar types provide no information beyond a name, description and optional `specifiedByURL`, while Enum types provide their values. Object and Interface types provide the fields they describe. Abstract types, Union and Interface, provide the Object types possible at runtime. List and NonNull types compose other types.
 */
export type __Type = {
  __typename?: "__Type";
  kind: __TypeKind;
  name?: Maybe<Scalars["String"]["output"]>;
  description?: Maybe<Scalars["String"]["output"]>;
  specifiedByURL?: Maybe<Scalars["String"]["output"]>;
  fields?: Maybe<Array<__Field>>;
  interfaces?: Maybe<Array<__Type>>;
  possibleTypes?: Maybe<Array<__Type>>;
  enumValues?: Maybe<Array<__EnumValue>>;
  inputFields?: Maybe<Array<__InputValue>>;
  ofType?: Maybe<__Type>;
  isOneOf?: Maybe<Scalars["Boolean"]["output"]>;
};

/**
 * The fundamental unit of any GraphQL Schema is the type. There are many kinds of types in GraphQL as represented by the `__TypeKind` enum.
 *
 * Depending on the kind of a type, certain fields describe information about that type. Scalar types provide no information beyond a name, description and optional `specifiedByURL`, while Enum types provide their values. Object and Interface types provide the fields they describe. Abstract types, Union and Interface, provide the Object types possible at runtime. List and NonNull types compose other types.
 */
export type __TypeFieldsArgs = {
  includeDeprecated?: InputMaybe<Scalars["Boolean"]["input"]>;
};

/**
 * The fundamental unit of any GraphQL Schema is the type. There are many kinds of types in GraphQL as represented by the `__TypeKind` enum.
 *
 * Depending on the kind of a type, certain fields describe information about that type. Scalar types provide no information beyond a name, description and optional `specifiedByURL`, while Enum types provide their values. Object and Interface types provide the fields they describe. Abstract types, Union and Interface, provide the Object types possible at runtime. List and NonNull types compose other types.
 */
export type __TypeEnumValuesArgs = {
  includeDeprecated?: InputMaybe<Scalars["Boolean"]["input"]>;
};

/**
 * The fundamental unit of any GraphQL Schema is the type. There are many kinds of types in GraphQL as represented by the `__TypeKind` enum.
 *
 * Depending on the kind of a type, certain fields describe information about that type. Scalar types provide no information beyond a name, description and optional `specifiedByURL`, while Enum types provide their values. Object and Interface types provide the fields they describe. Abstract types, Union and Interface, provide the Object types possible at runtime. List and NonNull types compose other types.
 */
export type __TypeInputFieldsArgs = {
  includeDeprecated?: InputMaybe<Scalars["Boolean"]["input"]>;
};

/** An enum describing what kind of type a given `__Type` is. */
export enum __TypeKind {
  /** Indicates this type is a scalar. */
  Scalar = "SCALAR",
  /** Indicates this type is an object. `fields` and `interfaces` are valid fields. */
  Object = "OBJECT",
  /** Indicates this type is an interface. `fields`, `interfaces`, and `possibleTypes` are valid fields. */
  Interface = "INTERFACE",
  /** Indicates this type is a union. `possibleTypes` is a valid field. */
  Union = "UNION",
  /** Indicates this type is an enum. `enumValues` is a valid field. */
  Enum = "ENUM",
  /** Indicates this type is an input object. `inputFields` is a valid field. */
  InputObject = "INPUT_OBJECT",
  /** Indicates this type is a list. `ofType` is a valid field. */
  List = "LIST",
  /** Indicates this type is a non-null. `ofType` is a valid field. */
  NonNull = "NON_NULL",
}

/** Object and Interface types are described by a list of Fields, each of which has a name, potentially a list of arguments, and a return type. */
export type __Field = {
  __typename?: "__Field";
  name: Scalars["String"]["output"];
  description?: Maybe<Scalars["String"]["output"]>;
  args: Array<__InputValue>;
  type: __Type;
  isDeprecated: Scalars["Boolean"]["output"];
  deprecationReason?: Maybe<Scalars["String"]["output"]>;
};

/** Object and Interface types are described by a list of Fields, each of which has a name, potentially a list of arguments, and a return type. */
export type __FieldArgsArgs = {
  includeDeprecated?: InputMaybe<Scalars["Boolean"]["input"]>;
};

/** Arguments provided to Fields or Directives and the input fields of an InputObject are represented as Input Values which describe their type and optionally a default value. */
export type __InputValue = {
  __typename?: "__InputValue";
  name: Scalars["String"]["output"];
  description?: Maybe<Scalars["String"]["output"]>;
  type: __Type;
  /** A GraphQL-formatted string representing the default value for this input value. */
  defaultValue?: Maybe<Scalars["String"]["output"]>;
  isDeprecated: Scalars["Boolean"]["output"];
  deprecationReason?: Maybe<Scalars["String"]["output"]>;
};

/** One possible value for a given Enum. Enum values are unique values, not a placeholder for a string or numeric value. However an Enum value is returned in a JSON response as a string. */
export type __EnumValue = {
  __typename?: "__EnumValue";
  name: Scalars["String"]["output"];
  description?: Maybe<Scalars["String"]["output"]>;
  isDeprecated: Scalars["Boolean"]["output"];
  deprecationReason?: Maybe<Scalars["String"]["output"]>;
};

export type GetCurrentAdminPermissionsQueryVariables = Exact<{
  [key: string]: never;
}>;

export type GetCurrentAdminPermissionsQuery = {
  __typename?: "Query";
  getCurrentAdminPermissions?: Array<{
    __typename?: "PermissionView";
    resource: Resources;
    action: Actions;
    id: { __typename?: "PermissionViewId"; permissionId: string };
  } | null> | null;
};

export type GetAdminPermissionsByAdminIdQueryVariables = Exact<{
  adminId: Scalars["String"]["input"];
}>;

export type GetAdminPermissionsByAdminIdQuery = {
  __typename?: "Query";
  getAdminPermissionsByAdminId?: Array<{
    __typename?: "Permission";
    id: string;
    description?: string | null;
    resource?: {
      __typename?: "Resource";
      id: string;
      resource?: Resources | null;
      description?: string | null;
    } | null;
    action?: {
      __typename?: "Action";
      actionId: string;
      action?: Actions | null;
      description?: string | null;
    } | null;
  } | null> | null;
};

export type RevokeAdminPermissionMutationVariables = Exact<{
  adminId: Scalars["String"]["input"];
  permissionId: Scalars["String"]["input"];
}>;

export type RevokeAdminPermissionMutation = {
  __typename?: "Mutation";
  revokeAdminPermission?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type GetAllPermissionsQueryVariables = Exact<{ [key: string]: never }>;

export type GetAllPermissionsQuery = {
  __typename?: "Query";
  getAllPermissions?: Array<{
    __typename?: "Permission";
    id: string;
    description?: string | null;
    resource?: {
      __typename?: "Resource";
      id: string;
      resource?: Resources | null;
      description?: string | null;
    } | null;
    action?: {
      __typename?: "Action";
      actionId: string;
      action?: Actions | null;
      description?: string | null;
    } | null;
  } | null> | null;
};

export type GetAllPermissionsNoDescriptionsQueryVariables = Exact<{
  [key: string]: never;
}>;

export type GetAllPermissionsNoDescriptionsQuery = {
  __typename?: "Query";
  getAllPermissions?: Array<{
    __typename?: "Permission";
    id: string;
    resource?: {
      __typename?: "Resource";
      id: string;
      resource?: Resources | null;
    } | null;
    action?: {
      __typename?: "Action";
      actionId: string;
      action?: Actions | null;
    } | null;
  } | null> | null;
};

export type CreatePermissionMutationVariables = Exact<{
  input: CreatePermissionInput;
}>;

export type CreatePermissionMutation = {
  __typename?: "Mutation";
  createPermission?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type GrantAdminPermissionMutationVariables = Exact<{
  adminId: Scalars["String"]["input"];
  permissionId: Scalars["String"]["input"];
}>;

export type GrantAdminPermissionMutation = {
  __typename?: "Mutation";
  grantAdminPermission?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type GetUserStatsQueryVariables = Exact<{ [key: string]: never }>;

export type GetUserStatsQuery = {
  __typename?: "Query";
  getUserStats?: {
    __typename?: "UserStats";
    total?: number | null;
    newUserTotal?: number | null;
    deletedUserTotal?: number | null;
  } | null;
};

export type GetAllUsersQueryVariables = Exact<{ [key: string]: never }>;

export type GetAllUsersQuery = {
  __typename?: "Query";
  getAllUsers?: Array<{
    __typename?: "User";
    id: string;
    email: string;
    name?: string | null;
    status?: string | null;
  } | null> | null;
};

export type GetCurrentAdminQueryVariables = Exact<{ [key: string]: never }>;

export type GetCurrentAdminQuery = {
  __typename?: "Query";
  getCurrentAdmin?: {
    __typename?: "Admin";
    userId: string;
    isSuperAdmin?: boolean | null;
    status?: string | null;
    isDeleted?: boolean | null;
  } | null;
};

export type GetAllAdminIdsQueryVariables = Exact<{ [key: string]: never }>;

export type GetAllAdminIdsQuery = {
  __typename?: "Query";
  getAllAdmins?: Array<{ __typename?: "Admin"; userId: string } | null> | null;
};

export type GetAllAdminsQueryVariables = Exact<{ [key: string]: never }>;

export type GetAllAdminsQuery = {
  __typename?: "Query";
  getAllAdmins?: Array<{
    __typename?: "Admin";
    userId: string;
    email?: string | null;
    isSuperAdmin?: boolean | null;
    status?: string | null;
    isDeleted?: boolean | null;
  } | null> | null;
};

export type CreateAdminMutationVariables = Exact<{
  userId: Scalars["String"]["input"];
}>;

export type CreateAdminMutation = {
  __typename?: "Mutation";
  createAdmin?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type DeactivateAdminMutationVariables = Exact<{
  userId: Scalars["String"]["input"];
}>;

export type DeactivateAdminMutation = {
  __typename?: "Mutation";
  deactivateAdmin?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type CreateChatMutationVariables = Exact<{
  conversationId: Scalars["String"]["input"];
  message: Scalars["String"]["input"];
}>;

export type CreateChatMutation = {
  __typename?: "Mutation";
  createChat?: {
    __typename?: "CreateChatResponse";
    chat?: {
      __typename?: "Chat";
      chatId: string;
      userId?: string | null;
      sender: string;
      message: string;
      createdAt?: string | null;
    } | null;
    response?: {
      __typename?: "Chat";
      chatId: string;
      userId?: string | null;
      sender: string;
      message: string;
      createdAt?: string | null;
    } | null;
  } | null;
};

export type ClearCurrentConversationMutationVariables = Exact<{
  conversationId: Scalars["String"]["input"];
}>;

export type ClearCurrentConversationMutation = {
  __typename?: "Mutation";
  clearCurrentConversation?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type GetCurrentConversationQueryVariables = Exact<{
  conversationId: Scalars["String"]["input"];
}>;

export type GetCurrentConversationQuery = {
  __typename?: "Query";
  getCurrentConversation?: Array<{
    __typename?: "Chat";
    chatId: string;
    userId?: string | null;
    sender: string;
    message: string;
    createdAt?: string | null;
  } | null> | null;
};

export type GetActionsEnumValuesQueryVariables = Exact<{
  [key: string]: never;
}>;

export type GetActionsEnumValuesQuery = {
  __typename?: "Query";
  Actions?: {
    __typename?: "__Type";
    name?: string | null;
    enumValues?: Array<{ __typename?: "__EnumValue"; name: string }> | null;
  } | null;
};

export type GetResoucesEnumValuesQueryVariables = Exact<{
  [key: string]: never;
}>;

export type GetResoucesEnumValuesQuery = {
  __typename?: "Query";
  Resources?: {
    __typename?: "__Type";
    name?: string | null;
    enumValues?: Array<{ __typename?: "__EnumValue"; name: string }> | null;
  } | null;
};

export type SearchItemsQueryVariables = Exact<{
  searchText?: InputMaybe<Scalars["String"]["input"]>;
  pagination?: InputMaybe<PaginationInput>;
  sorting?: InputMaybe<SortInput>;
}>;

export type SearchItemsQuery = {
  __typename?: "Query";
  searchForItems?: {
    __typename?: "SearchedItemsResponse";
    items?: Array<{
      __typename?: "Item";
      id?: string | null;
      name?: string | null;
      description?: string | null;
      isActive?: boolean | null;
      endingTime?: string | null;
      price?: number | null;
      stock?: number | null;
      category?: string | null;
      images?: Array<string | null> | null;
      seller?: {
        __typename?: "User";
        id: string;
        name?: string | null;
        email: string;
      } | null;
    } | null> | null;
    pagination?: {
      __typename?: "Pagination";
      total?: number | null;
      page?: number | null;
      size?: number | null;
    } | null;
    sorting?: {
      __typename?: "Sorting";
      sortBy?: string | null;
      sortDirection?: SortDirection | null;
    } | null;
  } | null;
};

export type GetShopItemsQueryVariables = Exact<{
  pagination?: InputMaybe<PaginationInput>;
  sorting?: InputMaybe<SortInput>;
}>;

export type GetShopItemsQuery = {
  __typename?: "Query";
  getShopItems?: {
    __typename?: "SearchedItemsResponse";
    items?: Array<{
      __typename?: "Item";
      id?: string | null;
      name?: string | null;
      description?: string | null;
      isActive?: boolean | null;
      endingTime?: string | null;
      price?: number | null;
      stock?: number | null;
      category?: string | null;
      images?: Array<string | null> | null;
      seller?: {
        __typename?: "User";
        id: string;
        name?: string | null;
        email: string;
      } | null;
    } | null> | null;
    pagination?: {
      __typename?: "Pagination";
      total?: number | null;
      page?: number | null;
      size?: number | null;
    } | null;
    sorting?: {
      __typename?: "Sorting";
      sortBy?: string | null;
      sortDirection?: SortDirection | null;
    } | null;
  } | null;
};

export type GetItemByIdQueryVariables = Exact<{
  id: Scalars["String"]["input"];
}>;

export type GetItemByIdQuery = {
  __typename?: "Query";
  getItemById?: {
    __typename?: "Item";
    id?: string | null;
    name?: string | null;
    description?: string | null;
    isActive?: boolean | null;
    endingTime?: string | null;
    price?: number | null;
    stock?: number | null;
    category?: string | null;
    images?: Array<string | null> | null;
    seller?: {
      __typename?: "User";
      id: string;
      name?: string | null;
      email: string;
    } | null;
  } | null;
};

export type GetItemsByUserQueryVariables = Exact<{
  id: Scalars["String"]["input"];
  isActive?: InputMaybe<Scalars["Boolean"]["input"]>;
  pagination?: InputMaybe<PaginationInput>;
}>;

export type GetItemsByUserQuery = {
  __typename?: "Query";
  getItemsByUser?: {
    __typename?: "SearchedItemsResponse";
    items?: Array<{
      __typename?: "Item";
      id?: string | null;
      name?: string | null;
      description?: string | null;
      isActive?: boolean | null;
      stock?: number | null;
    } | null> | null;
    pagination?: {
      __typename?: "Pagination";
      total?: number | null;
      page?: number | null;
      size?: number | null;
    } | null;
  } | null;
};

export type SaveItemMutationVariables = Exact<{
  itemInput: ItemInput;
}>;

export type SaveItemMutation = {
  __typename?: "Mutation";
  saveItem?: {
    __typename?: "Item";
    id?: string | null;
    name?: string | null;
    description?: string | null;
    isActive?: boolean | null;
    endingTime?: string | null;
    price?: number | null;
    stock?: number | null;
    category?: string | null;
    images?: Array<string | null> | null;
    seller?: {
      __typename?: "User";
      id: string;
      name?: string | null;
      email: string;
    } | null;
  } | null;
};

export type ReportBugMutationVariables = Exact<{
  title: Scalars["String"]["input"];
  description: Scalars["String"]["input"];
}>;

export type ReportBugMutation = {
  __typename?: "Mutation";
  reportBug?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type CreateUserMutationVariables = Exact<{
  input: UserInput;
}>;

export type CreateUserMutation = {
  __typename?: "Mutation";
  createUser?: { __typename?: "User"; id: string } | null;
};

export type SaveUserDetailsMutationVariables = Exact<{
  id: Scalars["String"]["input"];
  detailsInput: UserDetailsInput;
}>;

export type SaveUserDetailsMutation = {
  __typename?: "Mutation";
  saveUserDetails?: {
    __typename?: "User";
    id: string;
    details?: {
      __typename?: "UserDetails";
      id: string;
      contactNumber?: string | null;
      houseName?: string | null;
      addressStreet?: string | null;
      addressCity?: string | null;
      addressCounty?: string | null;
      addressPostcode?: string | null;
    } | null;
  } | null;
};

export type GetUserQueryVariables = Exact<{ [key: string]: never }>;

export type GetUserQuery = {
  __typename?: "Query";
  getUser?: {
    __typename?: "User";
    id: string;
    email: string;
    name?: string | null;
    status?: string | null;
    details?: {
      __typename?: "UserDetails";
      id: string;
      contactNumber?: string | null;
      houseName?: string | null;
      addressStreet?: string | null;
      addressCity?: string | null;
      addressCounty?: string | null;
      addressPostcode?: string | null;
    } | null;
  } | null;
};

export type DeleteUserMutationVariables = Exact<{ [key: string]: never }>;

export type DeleteUserMutation = {
  __typename?: "Mutation";
  deleteUser?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export type DeactivateUserMutationVariables = Exact<{
  id: Scalars["String"]["input"];
}>;

export type DeactivateUserMutation = {
  __typename?: "Mutation";
  deactivateUser?: {
    __typename?: "MutationResponse";
    success?: boolean | null;
    message?: string | null;
  } | null;
};

export const GetCurrentAdminPermissionsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getCurrentAdminPermissions" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getCurrentAdminPermissions" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                {
                  kind: "Field",
                  name: { kind: "Name", value: "id" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "permissionId" },
                      },
                    ],
                  },
                },
                { kind: "Field", name: { kind: "Name", value: "resource" } },
                { kind: "Field", name: { kind: "Name", value: "action" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetCurrentAdminPermissionsQuery,
  GetCurrentAdminPermissionsQueryVariables
>;
export const GetAdminPermissionsByAdminIdDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAdminPermissionsByAdminId" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "adminId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAdminPermissionsByAdminId" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "adminId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "adminId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "description" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "resource" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "resource" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "action" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "actionId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "action" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetAdminPermissionsByAdminIdQuery,
  GetAdminPermissionsByAdminIdQueryVariables
>;
export const RevokeAdminPermissionDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "revokeAdminPermission" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "adminId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "permissionId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "revokeAdminPermission" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "adminId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "adminId" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "permissionId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "permissionId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  RevokeAdminPermissionMutation,
  RevokeAdminPermissionMutationVariables
>;
export const GetAllPermissionsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAllPermissions" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAllPermissions" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "description" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "resource" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "resource" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "action" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "actionId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "action" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetAllPermissionsQuery,
  GetAllPermissionsQueryVariables
>;
export const GetAllPermissionsNoDescriptionsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAllPermissionsNoDescriptions" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAllPermissions" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "resource" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "resource" },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "action" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "actionId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "action" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetAllPermissionsNoDescriptionsQuery,
  GetAllPermissionsNoDescriptionsQueryVariables
>;
export const CreatePermissionDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "createPermission" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "input" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "CreatePermissionInput" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "createPermission" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "input" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "input" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  CreatePermissionMutation,
  CreatePermissionMutationVariables
>;
export const GrantAdminPermissionDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "grantAdminPermission" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "adminId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "permissionId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "grantAdminPermission" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "adminId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "adminId" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "permissionId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "permissionId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GrantAdminPermissionMutation,
  GrantAdminPermissionMutationVariables
>;
export const GetUserStatsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getUserStats" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getUserStats" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "total" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "newUserTotal" },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "deletedUserTotal" },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetUserStatsQuery, GetUserStatsQueryVariables>;
export const GetAllUsersDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAllUsers" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAllUsers" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "email" } },
                { kind: "Field", name: { kind: "Name", value: "name" } },
                { kind: "Field", name: { kind: "Name", value: "status" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetAllUsersQuery, GetAllUsersQueryVariables>;
export const GetCurrentAdminDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getCurrentAdmin" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getCurrentAdmin" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "userId" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "isSuperAdmin" },
                },
                { kind: "Field", name: { kind: "Name", value: "status" } },
                { kind: "Field", name: { kind: "Name", value: "isDeleted" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetCurrentAdminQuery,
  GetCurrentAdminQueryVariables
>;
export const GetAllAdminIdsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAllAdminIds" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAllAdmins" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "userId" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetAllAdminIdsQuery, GetAllAdminIdsQueryVariables>;
export const GetAllAdminsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getAllAdmins" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getAllAdmins" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "userId" } },
                { kind: "Field", name: { kind: "Name", value: "email" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "isSuperAdmin" },
                },
                { kind: "Field", name: { kind: "Name", value: "status" } },
                { kind: "Field", name: { kind: "Name", value: "isDeleted" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetAllAdminsQuery, GetAllAdminsQueryVariables>;
export const CreateAdminDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "CreateAdmin" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "userId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "createAdmin" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "userId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "userId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<CreateAdminMutation, CreateAdminMutationVariables>;
export const DeactivateAdminDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "DeactivateAdmin" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "userId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "deactivateAdmin" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "userId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "userId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  DeactivateAdminMutation,
  DeactivateAdminMutationVariables
>;
export const CreateChatDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "createChat" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "conversationId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "message" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "createChat" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "conversationId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "conversationId" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "message" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "message" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                {
                  kind: "Field",
                  name: { kind: "Name", value: "chat" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "chatId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "userId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sender" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "message" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "createdAt" },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "response" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "chatId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "userId" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sender" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "message" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "createdAt" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<CreateChatMutation, CreateChatMutationVariables>;
export const ClearCurrentConversationDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "clearCurrentConversation" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "conversationId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "clearCurrentConversation" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "conversationId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "conversationId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  ClearCurrentConversationMutation,
  ClearCurrentConversationMutationVariables
>;
export const GetCurrentConversationDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getCurrentConversation" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "conversationId" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getCurrentConversation" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "conversationId" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "conversationId" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "chatId" } },
                { kind: "Field", name: { kind: "Name", value: "userId" } },
                { kind: "Field", name: { kind: "Name", value: "sender" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
                { kind: "Field", name: { kind: "Name", value: "createdAt" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetCurrentConversationQuery,
  GetCurrentConversationQueryVariables
>;
export const GetActionsEnumValuesDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "GetActionsEnumValues" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            alias: { kind: "Name", value: "Actions" },
            name: { kind: "Name", value: "__type" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "name" },
                value: { kind: "StringValue", value: "Actions", block: false },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "name" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "enumValues" },
                  arguments: [
                    {
                      kind: "Argument",
                      name: { kind: "Name", value: "includeDeprecated" },
                      value: { kind: "BooleanValue", value: false },
                    },
                  ],
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetActionsEnumValuesQuery,
  GetActionsEnumValuesQueryVariables
>;
export const GetResoucesEnumValuesDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "GetResoucesEnumValues" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            alias: { kind: "Name", value: "Resources" },
            name: { kind: "Name", value: "__type" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "name" },
                value: {
                  kind: "StringValue",
                  value: "Resources",
                  block: false,
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "name" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "enumValues" },
                  arguments: [
                    {
                      kind: "Argument",
                      name: { kind: "Name", value: "includeDeprecated" },
                      value: { kind: "BooleanValue", value: false },
                    },
                  ],
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  GetResoucesEnumValuesQuery,
  GetResoucesEnumValuesQueryVariables
>;
export const SearchItemsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "SearchItems" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "searchText" },
          },
          type: { kind: "NamedType", name: { kind: "Name", value: "String" } },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "pagination" },
          },
          type: {
            kind: "NamedType",
            name: { kind: "Name", value: "PaginationInput" },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "sorting" },
          },
          type: {
            kind: "NamedType",
            name: { kind: "Name", value: "SortInput" },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "searchForItems" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "searchText" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "searchText" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "pagination" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "pagination" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "sorting" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "sorting" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                {
                  kind: "Field",
                  name: { kind: "Name", value: "items" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "isActive" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "endingTime" },
                      },
                      { kind: "Field", name: { kind: "Name", value: "price" } },
                      { kind: "Field", name: { kind: "Name", value: "stock" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "category" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "images" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "seller" },
                        selectionSet: {
                          kind: "SelectionSet",
                          selections: [
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "id" },
                            },
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "name" },
                            },
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "email" },
                            },
                          ],
                        },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "pagination" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "total" } },
                      { kind: "Field", name: { kind: "Name", value: "page" } },
                      { kind: "Field", name: { kind: "Name", value: "size" } },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "sorting" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sortBy" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sortDirection" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<SearchItemsQuery, SearchItemsQueryVariables>;
export const GetShopItemsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getShopItems" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "pagination" },
          },
          type: {
            kind: "NamedType",
            name: { kind: "Name", value: "PaginationInput" },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "sorting" },
          },
          type: {
            kind: "NamedType",
            name: { kind: "Name", value: "SortInput" },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getShopItems" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "pagination" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "pagination" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "sorting" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "sorting" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                {
                  kind: "Field",
                  name: { kind: "Name", value: "items" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "isActive" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "endingTime" },
                      },
                      { kind: "Field", name: { kind: "Name", value: "price" } },
                      { kind: "Field", name: { kind: "Name", value: "stock" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "category" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "images" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "seller" },
                        selectionSet: {
                          kind: "SelectionSet",
                          selections: [
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "id" },
                            },
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "name" },
                            },
                            {
                              kind: "Field",
                              name: { kind: "Name", value: "email" },
                            },
                          ],
                        },
                      },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "pagination" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "total" } },
                      { kind: "Field", name: { kind: "Name", value: "page" } },
                      { kind: "Field", name: { kind: "Name", value: "size" } },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "sorting" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sortBy" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "sortDirection" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetShopItemsQuery, GetShopItemsQueryVariables>;
export const GetItemByIdDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "GetItemById" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: { kind: "Variable", name: { kind: "Name", value: "id" } },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getItemById" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "id" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "id" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "name" } },
                { kind: "Field", name: { kind: "Name", value: "description" } },
                { kind: "Field", name: { kind: "Name", value: "isActive" } },
                { kind: "Field", name: { kind: "Name", value: "endingTime" } },
                { kind: "Field", name: { kind: "Name", value: "price" } },
                { kind: "Field", name: { kind: "Name", value: "stock" } },
                { kind: "Field", name: { kind: "Name", value: "category" } },
                { kind: "Field", name: { kind: "Name", value: "images" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "seller" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                      { kind: "Field", name: { kind: "Name", value: "email" } },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetItemByIdQuery, GetItemByIdQueryVariables>;
export const GetItemsByUserDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "GetItemsByUser" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: { kind: "Variable", name: { kind: "Name", value: "id" } },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "isActive" },
          },
          type: { kind: "NamedType", name: { kind: "Name", value: "Boolean" } },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "pagination" },
          },
          type: {
            kind: "NamedType",
            name: { kind: "Name", value: "PaginationInput" },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getItemsByUser" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "id" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "id" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "isActive" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "isActive" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "pagination" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "pagination" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                {
                  kind: "Field",
                  name: { kind: "Name", value: "items" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "description" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "isActive" },
                      },
                      { kind: "Field", name: { kind: "Name", value: "stock" } },
                    ],
                  },
                },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "pagination" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "total" } },
                      { kind: "Field", name: { kind: "Name", value: "page" } },
                      { kind: "Field", name: { kind: "Name", value: "size" } },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetItemsByUserQuery, GetItemsByUserQueryVariables>;
export const SaveItemDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "SaveItem" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "itemInput" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "ItemInput" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "saveItem" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "itemInput" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "itemInput" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "name" } },
                { kind: "Field", name: { kind: "Name", value: "description" } },
                { kind: "Field", name: { kind: "Name", value: "isActive" } },
                { kind: "Field", name: { kind: "Name", value: "endingTime" } },
                { kind: "Field", name: { kind: "Name", value: "price" } },
                { kind: "Field", name: { kind: "Name", value: "stock" } },
                { kind: "Field", name: { kind: "Name", value: "category" } },
                { kind: "Field", name: { kind: "Name", value: "images" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "seller" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      { kind: "Field", name: { kind: "Name", value: "name" } },
                      { kind: "Field", name: { kind: "Name", value: "email" } },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<SaveItemMutation, SaveItemMutationVariables>;
export const ReportBugDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "ReportBug" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "title" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "description" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "reportBug" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "title" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "title" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "description" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "description" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<ReportBugMutation, ReportBugMutationVariables>;
export const CreateUserDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "CreateUser" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "input" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "UserInput" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "createUser" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "userInput" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "input" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<CreateUserMutation, CreateUserMutationVariables>;
export const SaveUserDetailsDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "SaveUserDetails" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: { kind: "Variable", name: { kind: "Name", value: "id" } },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
        {
          kind: "VariableDefinition",
          variable: {
            kind: "Variable",
            name: { kind: "Name", value: "detailsInput" },
          },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "UserDetailsInput" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "saveUserDetails" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "id" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "id" },
                },
              },
              {
                kind: "Argument",
                name: { kind: "Name", value: "detailsInput" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "detailsInput" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "details" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "contactNumber" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "houseName" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressStreet" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressCity" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressCounty" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressPostcode" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  SaveUserDetailsMutation,
  SaveUserDetailsMutationVariables
>;
export const GetUserDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "query",
      name: { kind: "Name", value: "getUser" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "getUser" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "id" } },
                { kind: "Field", name: { kind: "Name", value: "email" } },
                { kind: "Field", name: { kind: "Name", value: "name" } },
                { kind: "Field", name: { kind: "Name", value: "status" } },
                {
                  kind: "Field",
                  name: { kind: "Name", value: "details" },
                  selectionSet: {
                    kind: "SelectionSet",
                    selections: [
                      { kind: "Field", name: { kind: "Name", value: "id" } },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "contactNumber" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "houseName" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressStreet" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressCity" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressCounty" },
                      },
                      {
                        kind: "Field",
                        name: { kind: "Name", value: "addressPostcode" },
                      },
                    ],
                  },
                },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<GetUserQuery, GetUserQueryVariables>;
export const DeleteUserDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "DeleteUser" },
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "deleteUser" },
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<DeleteUserMutation, DeleteUserMutationVariables>;
export const DeactivateUserDocument = {
  kind: "Document",
  definitions: [
    {
      kind: "OperationDefinition",
      operation: "mutation",
      name: { kind: "Name", value: "DeactivateUser" },
      variableDefinitions: [
        {
          kind: "VariableDefinition",
          variable: { kind: "Variable", name: { kind: "Name", value: "id" } },
          type: {
            kind: "NonNullType",
            type: {
              kind: "NamedType",
              name: { kind: "Name", value: "String" },
            },
          },
        },
      ],
      selectionSet: {
        kind: "SelectionSet",
        selections: [
          {
            kind: "Field",
            name: { kind: "Name", value: "deactivateUser" },
            arguments: [
              {
                kind: "Argument",
                name: { kind: "Name", value: "id" },
                value: {
                  kind: "Variable",
                  name: { kind: "Name", value: "id" },
                },
              },
            ],
            selectionSet: {
              kind: "SelectionSet",
              selections: [
                { kind: "Field", name: { kind: "Name", value: "success" } },
                { kind: "Field", name: { kind: "Name", value: "message" } },
              ],
            },
          },
        ],
      },
    },
  ],
} as unknown as DocumentNode<
  DeactivateUserMutation,
  DeactivateUserMutationVariables
>;
