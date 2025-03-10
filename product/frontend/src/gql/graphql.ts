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
  createUser?: Maybe<User>;
  deleteUser?: Maybe<MutationResponse>;
  reportBug?: Maybe<MutationResponse>;
  saveItem?: Maybe<Item>;
  saveUserDetails?: Maybe<User>;
};

export type MutationCreateUserArgs = {
  userInput?: InputMaybe<UserInput>;
};

export type MutationReportBugArgs = {
  description: Scalars["String"]["input"];
  title: Scalars["String"]["input"];
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

export type Query = {
  __typename?: "Query";
  _service: _Service;
  getItemById?: Maybe<Item>;
  getItemsByUser?: Maybe<SearchedItemsResponse>;
  getShopItems?: Maybe<SearchedItemsResponse>;
  getUser?: Maybe<User>;
  searchForItems?: Maybe<SearchedItemsResponse>;
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

export type _Service = {
  __typename?: "_Service";
  sdl: Scalars["String"]["output"];
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
    sorting?: {
      __typename?: "Sorting";
      sortBy?: string | null;
      sortDirection?: SortDirection | null;
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
