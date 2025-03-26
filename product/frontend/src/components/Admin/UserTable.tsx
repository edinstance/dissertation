"use client";
import { Actions, Resources, User } from "@/gql/graphql";
import { GET_ADMIN_IDS, GET_ALL_USERS } from "@/lib/graphql/admin";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useQuery } from "@apollo/client";
import { ColumnDef } from "@tanstack/react-table";
import { useEffect, useState } from "react";
import LoadingSpinner from "../ui/LoadingSpinner";
import { Table } from "../ui/Table";

function UserTable() {
  const { loading, data } = useQuery(GET_ALL_USERS);
  const [users, setUsers] = useState<User[]>([]);

  const { hasPermission } = useAdminPermissionsStore();

  useEffect(() => {
    if (data && data.getAllUsers) {
      setUsers(data.getAllUsers.filter((user): user is User => user !== null));
    }
  }, [data]);

  const columns: ColumnDef<User>[] = [
    {
      header: "ID",
      accessorKey: "id",
    },
    {
      header: "Email",
      accessorKey: "email",
    },
    {
      header: "Name",
      accessorKey: "name",
    },
    {
      header: "Status",
      accessorKey: "status",
    },
  ];

  if (hasPermission(Resources.Admins, Actions.Read)) {
    const { data: adminDataResult } = useQuery(GET_ADMIN_IDS);
    const adminData = adminDataResult?.getAllAdmins;

    columns.push({
      header: "Admin",
      accessorKey: "admin",
      cell: ({ row }) =>
        adminData?.some((admin) => admin && admin.userId === row.original.id)
          ? "Yes"
          : "No",
    });
  }

  if (loading) {
    return <LoadingSpinner />;
  }

  return <Table data={users} columns={columns} />;
}

export default UserTable;
