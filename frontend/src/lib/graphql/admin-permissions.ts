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

export const REVOKE_PERMISSION_FROM_ADMIN = graphql(`
  mutation revokeAdminPermission($adminId: String!, $permissionId: String!) {
    revokeAdminPermission(adminId: $adminId, permissionId: $permissionId) {
      success
      message
    }
  }
`);

export const GET_ALL_PERMISSIONS = graphql(`
  query getAllPermissions {
    getAllPermissions {
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

export const GET_ALL_PERMISSIONS_NO_DESCRIPTIONS = graphql(`
  query getAllPermissionsNoDescriptions {
    getAllPermissions {
      id
      resource {
        id
        resource
      }
      action {
        actionId
        action
      }
    }
  }
`);

export const CREATE_PERMISSION = graphql(`
  mutation createPermission($input: CreatePermissionInput!) {
    createPermission(input: $input) {
      success
      message
    }
  }
`);

export const GRANT_ADMIN_PERMISSION = graphql(`
  mutation grantAdminPermission($adminId: String!, $permissionId: String!) {
    grantAdminPermission(adminId: $adminId, permissionId: $permissionId) {
      success
      message
    }
  }
`);
