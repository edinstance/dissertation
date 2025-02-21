import { graphql } from "@/gql";

export const SEARCH_FOR_ITEMS = graphql(`
  query SearchItems($searchText: String) {
    searchForItems(searchText: $searchText) {
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
