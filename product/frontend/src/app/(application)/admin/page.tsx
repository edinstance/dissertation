"use client";

import useAdminPermissionsStore from "@/stores/AdminStore";
import { useSession } from "next-auth/react";

export default function Admin() {
  const { currentAdmin, adminPermissions } = useAdminPermissionsStore();
  const session = useSession();

  return (
    <div className="rounded-lg bg-gray-100 p-6 shadow-md">
      <h2 className="mb-4 text-xl">Admin Information</h2>
      <div className="mb-4">
        <p className="text-lg">
          Name:&nbsp;
          {session.data?.user?.name}
        </p>
        <p className="text-lg">
          Email:&nbsp;
          {session.data?.user?.email}
        </p>
        <p className="text-lg">ID:&nbsp;{currentAdmin?.userId}</p>
      </div>

      <h3 className="mb-2 text-lg">Permissions</h3>
      <ul className="list-disc pl-5">
        {adminPermissions.map((p) => (
          <li key={p.id.permissionId} className="text-lg">
            {p.action} {p.resource}
          </li>
        ))}
      </ul>
    </div>
  );
}
