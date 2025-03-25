"use client";
import { User } from "@/gql/graphql";
import { GET_ALL_USERS } from "@/lib/graphql/admin";
import { useQuery } from "@apollo/client";
import { ColumnDef } from "@tanstack/react-table";
import { useEffect, useState } from "react";
import LoadingSpinner from "../ui/LoadingSpinner";
import { Table } from "../ui/Table";

function UserTable() {
  const { loading, data } = useQuery(GET_ALL_USERS);
  const [users, setUsers] = useState<User[]>([]);

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

  if (loading) {
    return <LoadingSpinner />;
  }

  return <Table data={users} columns={columns} />;
}

export default UserTable;
