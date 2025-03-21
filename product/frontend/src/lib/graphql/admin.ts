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

export const GET_ALL_USERS = graphql(`
  query getAllUsers {
    getAllUsers {
      id
      email
      name
      status
    }
  }
`);
