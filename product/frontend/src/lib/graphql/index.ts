import { graphql } from "@/gql";

export const GET_SHOWS_QUERY = graphql(`
  query GetShows {
    shows {
        title
    }
  }`);
