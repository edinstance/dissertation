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
