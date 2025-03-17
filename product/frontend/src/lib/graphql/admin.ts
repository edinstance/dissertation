import { graphql } from "@/gql";

export const GET_USER_STATS = graphql(`
  query getUserStats {
    getUserStats {
      total
      newUserTotal
      deletedUserTotal
    }
  }
`);
