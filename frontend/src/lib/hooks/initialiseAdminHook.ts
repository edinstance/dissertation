"use client";
import { PermissionView } from "@/gql/graphql";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useQuery } from "@apollo/client";
import { GET_CURRENT_ADMIN } from "../graphql/admin";
import { GET_CURRENT_ADMIN_PERMISSIONS } from "../graphql/admin-permissions";

export const useAdminInitialization = () => {
  const { setCurrentAdmin, setAdminPermissions, clearCurrentAdmin } =
    useAdminPermissionsStore();

  const {
    data: adminData,
    loading: adminLoading,
    error: adminError,
  } = useQuery(GET_CURRENT_ADMIN, {
    fetchPolicy: "network-only",
    onCompleted: (data) => {
      if (data?.getCurrentAdmin) {
        setCurrentAdmin({
          userId: data.getCurrentAdmin.userId,
          isSuperAdmin: data.getCurrentAdmin.isSuperAdmin,
          status: data.getCurrentAdmin.status,
          isDeleted: data.getCurrentAdmin.isDeleted,
        });
      } else {
        clearCurrentAdmin();
      }
    },
    onError: () => {
      clearCurrentAdmin();
    },
  });

  // Fetch admin permissions
  const {
    data: permissionsData,
    loading: permissionsLoading,
    error: permissionsError,
  } = useQuery(GET_CURRENT_ADMIN_PERMISSIONS, {
    fetchPolicy: "network-only",
    skip: !!adminError || !adminData?.getCurrentAdmin,
    onCompleted: (data) => {
      if (data?.getCurrentAdminPermissions) {
        setAdminPermissions(
          data.getCurrentAdminPermissions.filter(
            (permission): permission is PermissionView => permission !== null,
          ),
        );
      }
    },
  });

  const isLoading = adminLoading || permissionsLoading;
  const isError = !!adminError || !!permissionsError;

  return {
    admin: adminData?.getCurrentAdmin,
    permissions: permissionsData?.getCurrentAdminPermissions,
    isLoading,
    isError,
  };
};
