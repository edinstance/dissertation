"use client";

import { Button } from "@/components/ui/Button";
import Divivder from "@/components/ui/Divider";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import { Select, SelectOption } from "@/components/ui/Select";
import { Actions, Resources } from "@/gql/graphql";
import {
  GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
  GET_ALL_PERMISSIONS_NO_DESCRIPTIONS,
  GRANT_ADMIN_PERMISSION,
  REVOKE_PERMISSION_FROM_ADMIN,
} from "@/lib/graphql/admin-permissions";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useMutation, useQuery } from "@apollo/client";
import { notFound } from "next/navigation";
import { use, useEffect, useMemo, useState } from "react";

function AdminPermissionsPage({
  params,
}: {
  params: Promise<{ adminId: string }>;
}) {
  const resolvedParams = use(params);
  const { adminId } = resolvedParams;
  const { hasPermission } = useAdminPermissionsStore();

  useEffect(() => {
    if (!hasPermission(Resources.AdminPermissions, Actions.Read)) {
      notFound();
    }
  }, [hasPermission]);

  const { data: adminPermissionsData, loading } = useQuery(
    GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
    {
      variables: { adminId },
    },
  );

  const { data: permissionOptions } = useQuery(
    GET_ALL_PERMISSIONS_NO_DESCRIPTIONS,
  );

  const [revokeAdminPermissionMutation] = useMutation(
    REVOKE_PERMISSION_FROM_ADMIN,
    {
      refetchQueries: [
        GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
        "getAdminPermissionsByAdminId",
      ],
    },
  );

  const [grantAdminPermissionMutation, { loading: grantPermissionLoading }] =
    useMutation(GRANT_ADMIN_PERMISSION, {
      refetchQueries: [
        GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
        "getAdminPermissionsByAdminId",
      ],
    });

  const [currentPermissionOption, setCurrentPermissionOption] =
    useState<SelectOption | null>(null);

  const permissionSelectOptions = useMemo<SelectOption[]>(() => {
    return (permissionOptions?.getAllPermissions ?? [])
      .filter(
        (permission): permission is NonNullable<typeof permission> =>
          permission !== null,
      )
      .map((permission) => ({
        value: permission.id,
        label: `${permission.action?.action} - ${permission.resource?.resource}`,
      }));
  }, [permissionOptions]);

  const adminPermissions = useMemo(() => {
    return (adminPermissionsData?.getAdminPermissionsByAdminId ?? []).filter(
      (permission): permission is NonNullable<typeof permission> =>
        permission !== null,
    );
  }, [adminPermissionsData]);

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="rounded-lg bg-gray-100 p-6 text-black shadow-md dark:bg-gray-800 dark:text-white">
      <div className="flex flex-row items-center justify-between space-y-4">
        <h2 className="text-xl">Admin Permissions</h2>

        <Button href="/admin/admins" variant="outline" className="mt-0">
          Return
        </Button>
      </div>
      <p className="mb-4 text-lg">Admin ID: {adminId}</p>

      {hasPermission(Resources.AdminPermissions, Actions.Create) && (
        <div className="py-4">
          <Divivder className="pb-4" />
          <p> Add Permissions to this admin</p>
          <div className="flex flex-row items-center space-x-4 pb-4">
            <div className="w-64">
              <Select
                options={permissionSelectOptions}
                value={currentPermissionOption}
                onChange={(option) => setCurrentPermissionOption(option)}
              />
            </div>
            <Button
              className="mt-0"
              disabled={
                currentPermissionOption === null || grantPermissionLoading
              }
              onClick={() => {
                if (currentPermissionOption) {
                  grantAdminPermissionMutation({
                    variables: {
                      adminId: adminId,
                      permissionId: String(currentPermissionOption.value),
                    },
                  });
                  setCurrentPermissionOption(null);
                }
              }}
            >
              {grantPermissionLoading ? "Granting..." : "Grant Permission"}
            </Button>
          </div>
        </div>
      )}

      <Divivder />
      <ul className="space-y-4 rounded-lg bg-gray-100 p-6 pt-4 text-lg dark:bg-gray-800">
        {adminPermissions && adminPermissions.length > 0 && (
          <div>
            {adminPermissions.map((permission) => (
              <li key={permission.id} className="space-y-4">
                <div className="text-md flex flex-row items-center justify-between">
                  <div>
                    <div className="flex flex-row items-center">
                      <p className="min-w-32">{permission.action?.action}</p>
                      {permission.action?.description && (
                        <p className="ml-2 text-sm text-gray-600 dark:text-gray-400">
                          ({permission.action.description})
                        </p>
                      )}
                    </div>
                    <div className="flex flex-row items-center">
                      <p className="min-w-32">
                        {permission.resource?.resource}
                      </p>
                      {permission.resource?.description && (
                        <p className="ml-2 text-sm text-gray-600 dark:text-gray-400">
                          ({permission.resource.description})
                        </p>
                      )}
                    </div>
                  </div>

                  <Button
                    color="destructive"
                    onClick={() => {
                      revokeAdminPermissionMutation({
                        variables: {
                          adminId: adminId,
                          permissionId: permission.id,
                        },
                      });
                    }}
                  >
                    Revoke
                  </Button>
                </div>
                <Divivder />
              </li>
            ))}
          </div>
        )}
      </ul>
    </div>
  );
}

export default AdminPermissionsPage;
