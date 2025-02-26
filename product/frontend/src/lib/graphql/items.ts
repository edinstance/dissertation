import { graphql } from "@/gql";

export const SEARCH_FOR_ITEMS = graphql(`
  query SearchItems($searchText: String) {
    searchForItems(searchText: $searchText) {
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
