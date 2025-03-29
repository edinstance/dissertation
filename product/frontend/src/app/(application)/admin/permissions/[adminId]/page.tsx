"use client";

import { Button } from "@/components/ui/Button";
import Divivder from "@/components/ui/Divider";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import {
  GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
  REVOKE_PERMISSION_FROM_ADMIN,
} from "@/lib/graphql/admin-permissions";
import { useMutation, useQuery } from "@apollo/client";
import { use, useMemo } from "react";

function AdminPermissionsPage({
  params,
}: {
  params: Promise<{ adminId: string }>;
}) {
  const resolvedParams = use(params);
  const { adminId } = resolvedParams;

  const { data, loading } = useQuery(GET_ADMIN_PERMISSIONS_BY_ADMIN_ID, {
    variables: { adminId },
  });

  const [revokeAdminPermissionMutation] = useMutation(
    REVOKE_PERMISSION_FROM_ADMIN,
    {
      refetchQueries: [
        GET_ADMIN_PERMISSIONS_BY_ADMIN_ID,
        "getAdminPermissionsByAdminId",
      ],
    },
  );

  const adminPermissions = useMemo(() => {
    return (data?.getAdminPermissionsByAdminId ?? []).filter(
      (permission): permission is NonNullable<typeof permission> =>
        permission !== null,
    );
  }, [data]);

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="rounded-lg bg-gray-100 p-6 text-black shadow-md dark:bg-gray-800 dark:text-white">
      <div className="flex flex-row items-center justify-between space-y-4">
        <h2 className="text-xl">Admin Permissions</h2>
        <div className="flex flex-row items-center space-x-4"> 
          <Button href="/admin/admins" className="mt-0">
            Add permission
          </Button>
          <Button href="/admin/admins" variant="outline" className="mt-0">
            Return
          </Button>
        </div>
      </div>
      <p className="mb-4 text-lg">Admin ID: {adminId}</p>
      <div></div>

      <ul className="space-y-4 rounded-lg bg-gray-100 p-6 pt-4 text-lg">
        {adminPermissions && adminPermissions.length > 0 && (
          <div>
            <Divivder />
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
