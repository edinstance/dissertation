import { graphql } from "@/gql";

export const GET_CURRENT_ADMIN_PERMISSIONS = graphql(`
  query getCurrentAdminPermissions {
    getCurrentAdminPermissions {
      id {
        permissionId
      }
      grantType
      resource
      action
    }
  }
`);
