import { graphql } from "@/gql";

export const GET_CURRENT_ADMIN_PERMISSIONS = graphql(`
  query getCurrentAdminPermissions {
    getCurrentAdminPermissions {
      id {
        permissionId
      }
      resource
      action
    }
  }
`);

export const GET_ADMIN_PERMISSIONS_BY_ADMIN_ID = graphql(`
  query getAdminPermissionsByAdminId($adminId: String!) {
    getAdminPermissionsByAdminId(adminId: $adminId) {
      id
      description
      resource {
        id
        resource
        description
      }
      action {
        actionId
        action
        description
      }
    }
  }
`);
