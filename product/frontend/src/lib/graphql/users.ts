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
        houseName
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
        houseName
        addressStreet
        addressCity
        addressCounty
        addressPostcode
      }
    }
  }
`);

export const DELETE_USER_MUTATION = graphql(`
  mutation DeleteUser {
    deleteUser {
      success
      message
    }
  }
`);

export const DEACTIVATE_USER_MUTATION = graphql(`
  mutation DeactivateUser($id: String!) {
    deactivateUser(id: $id) {
      success
      message
    }
  }
`);
