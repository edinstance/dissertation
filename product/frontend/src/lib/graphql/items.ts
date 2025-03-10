import { graphql } from "@/gql";

export const SEARCH_FOR_ITEMS = graphql(`
  query SearchItems(
    $searchText: String
    $pagination: PaginationInput
    $sorting: SortInput
  ) {
    searchForItems(
      searchText: $searchText
      pagination: $pagination
      sorting: $sorting
    ) {
      items {
        id
        name
        description
        isActive
        endingTime
        price
        stock
        category
        images
        seller {
          id
          name
          email
        }
      }
      pagination {
        total
        page
        size
      }
      sorting {
        sortBy
        sortDirection
      }
    }
  }
`);

export const GET_SHOP_ITEMS = graphql(`
  query getShopItems(
    $pagination: PaginationInput
    $sorting: SortInput
  ) {
    getShopItems(
      pagination: $pagination
      sorting: $sorting
    ) {
      items {
        id
        name
        description
        isActive
        endingTime
        price
        stock
        category
        images
        seller {
          id
          name
          email
        }
      }
      pagination {
        total
        page
        size
      }
      sorting {
        sortBy
        sortDirection
      }
    }
  }
`);

export const GET_ITEM_BY_ID_QUERY = graphql(`
  query GetItemById($id: String!) {
    getItemById(id: $id) {
      id
      name
      description
      isActive
      endingTime
      price
      stock
      category
      images
      seller {
        id
        name
        email
      }
    }
  }
`);

export const GET_ITEMS_BY_USER_QUERY = graphql(`
  query GetItemsByUser(
    $id: String!
    $isActive: Boolean
    $pagination: PaginationInput
  ) {
    getItemsByUser(id: $id, isActive: $isActive, pagination: $pagination) {
      items {
        id
        name
        description
        isActive
        stock
      }
      pagination {
        total
        page
        size
      }
      sorting {
        sortBy
        sortDirection
      }
    }
  }
`);
export const SAVE_ITEM_MUTATION = graphql(`
  mutation SaveItem($itemInput: ItemInput!) {
    saveItem(itemInput: $itemInput) {
      id
      name
      description
      isActive
      endingTime
      price
      stock
      category
      images
      seller {
        id
        name
        email
      }
    }
  }
`);
