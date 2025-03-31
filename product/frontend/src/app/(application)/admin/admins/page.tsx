"use client";
import AdminTable from "@/components/Admin/AdminTable";
import { Actions, Resources } from "@/gql/graphql";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { notFound } from "next/navigation";
import { useEffect } from "react";

export default function Admins() {
  const { hasPermission } = useAdminPermissionsStore();

  useEffect(() => {
    if (!hasPermission(Resources.Permissions, Actions.Read)) {
      notFound();
    }
  }, [hasPermission]);

  return (
    <div>
      <AdminTable />
    </div>
  );
}
