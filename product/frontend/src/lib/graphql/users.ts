import { graphql } from "@/gql";

export const CREATE_USER_MUTATION = graphql(`
  mutation CreateUser($input: UserInput!) {
    createUser(userInput: $input) {
      id
    }
  }
`);

export const SAVE_USER_DETAILS_MUTATION = graphql(`
  mutation SaveUserDetails($id: String!, $detailsInput: UserDetailsInput!) {
    saveUserDetails(id: $id, detailsInput: $detailsInput) {
      id
      details {
        id
        contactNumber
        addressStreet
        addressCity
        addressCounty
        addressPostcode
      }
    }
  }
`);

export const GET_USER = graphql(`
  query getUser {
    getUser {
      id
      email
      name
      details {
        id
        contactNumber
        addressStreet
        addressCity
        addressCounty
        addressPostcode
      }
    }
  }
`);
