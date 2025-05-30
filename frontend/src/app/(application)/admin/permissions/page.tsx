"use client";
import PermissionsInformation from "@/components/Admin/PermissionsInformation";
import { Actions, Resources } from "@/gql/graphql";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { notFound } from "next/navigation";
import { useEffect } from "react";

export default function Permissions() {
  const { hasPermission } = useAdminPermissionsStore();

  useEffect(() => {
    if (!hasPermission(Resources.Permissions, Actions.Read)) {
      notFound();
    }
  }, [hasPermission]);
  return (
    <div>
      <PermissionsInformation />
    </div>
  );
}
