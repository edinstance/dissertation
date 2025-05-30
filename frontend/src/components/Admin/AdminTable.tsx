"use client";
import { Actions, Admin, Resources } from "@/gql/graphql";
import { DEACTIVATE_ADMIN_MUTATION, GET_ALL_ADMINS } from "@/lib/graphql/admin";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useMutation, useQuery } from "@apollo/client";
import { ColumnDef } from "@tanstack/react-table";
import { redirect } from "next/navigation";
import { useEffect, useState } from "react";
import DropDown from "../ui/DropDown";
import LoadingSpinner from "../ui/LoadingSpinner";
import { Table } from "../ui/Table";

function AdminTable() {
  const { data, loading } = useQuery(GET_ALL_ADMINS);
  const [admins, setAdmins] = useState<Admin[]>([]);

  const { isCurrentAdminSuperAdmin } = useAdminPermissionsStore();

  useEffect(() => {
    if (data && data.getAllAdmins) {
      setAdmins(
        data.getAllAdmins.filter((admin): admin is Admin => admin !== null),
      );
    }
  }, [data]);

  const columns: ColumnDef<Admin>[] = [
    {
      header: "ID",
      accessorKey: "userId",
    },
    {
      header: "Email",
      accessorKey: "email",
    },
    {
      header: "Status",
      accessorKey: "status",
    },
  ];

  if (isCurrentAdminSuperAdmin()) {
    columns.push({
      header: "Super Admin",
      accessorKey: "isSuperAdmin",
      sortingFn: (rowA, rowB) => {
        return rowA.original.isSuperAdmin === rowB.original.isSuperAdmin
          ? 0
          : rowA.original.isSuperAdmin
            ? -1
            : 1;
      },
      cell: ({ row }) => (row.original.isSuperAdmin ? "Yes" : "No"),
    });
  }

  columns.push({
    header: "Actions",
    accessorKey: "actions",
    cell: ({ row }) => <AdminActions admin={row.original}></AdminActions>,
  });

  if (loading) {
    return <LoadingSpinner />;
  }

  return <Table data={admins} columns={columns} />;
}

export default AdminTable;

function AdminActions({ admin }: { admin: Admin }) {
  const { hasPermission } = useAdminPermissionsStore();

  const [deactivateAdmin] = useMutation(DEACTIVATE_ADMIN_MUTATION, {
    refetchQueries: [{ query: GET_ALL_ADMINS }],
  });

  const options = [];

  if (hasPermission(Resources.AdminPermissions, Actions.Read)) {
    options.push({
      label: "View Permissions",
      onClick: () => {
        redirect(`/admin/permissions/${admin.userId}`);
      },
    });
  }

  if (hasPermission(Resources.Admins, Actions.Delete)) {
    options.push({
      label: "Deactivate Admin",
      destructive: true,
      onClick: () => {
        deactivateAdmin({
          variables: {
            userId: admin.userId,
          },
        });
      },
    });
  }

  return (
    <DropDown
      title="Actions"
      options={options}
      disabled={options.length == 0}
    />
  );
}
