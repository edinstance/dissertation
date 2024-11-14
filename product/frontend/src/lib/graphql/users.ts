import { graphql } from "@/gql";

export const CREATE_USER_MUTATION = graphql(`
  mutation CreateUser($input: UserInput!) {
    createUser(userInput: $input) {
      id
    }
  }
`);
