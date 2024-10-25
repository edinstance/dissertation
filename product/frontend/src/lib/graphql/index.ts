import gql from "graphql-tag";

export const GET_SHOWS_QUERY = gql(`
  query GetShows {
    shows {
        title
    }
  }`);
