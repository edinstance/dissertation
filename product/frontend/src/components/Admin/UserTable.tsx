"use client";
import { addUserToAdminGroup } from "@/actions/add-user-to-admin-group";
import { Actions, Resources, User } from "@/gql/graphql";
import {
  CREATE_ADMIN_MUTATION,
  GET_ADMIN_IDS,
  GET_ALL_USERS,
} from "@/lib/graphql/admin";
import { DEACTIVATE_USER_MUTATION } from "@/lib/graphql/users";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useMutation, useQuery } from "@apollo/client";
import { ColumnDef } from "@tanstack/react-table";
import { useEffect, useState } from "react";
import DropDown from "../ui/DropDown";
import LoadingSpinner from "../ui/LoadingSpinner";
import { Table } from "../ui/Table";

function UserTable() {
  const { loading, data } = useQuery(GET_ALL_USERS);
  const { data: adminDataResult } = useQuery(GET_ADMIN_IDS);
  const [users, setUsers] = useState<User[]>([]);
  const [adminIds, setAdminIds] = useState<string[]>([]);

  const { hasPermission } = useAdminPermissionsStore();

  useEffect(() => {
    if (data && data.getAllUsers) {
      setUsers(data.getAllUsers.filter((user): user is User => user !== null));
    }
  }, [data]);

  useEffect(() => {
    if (adminDataResult && adminDataResult.getAllAdmins) {
      const ids = adminDataResult.getAllAdmins
        .filter((admin) => admin && admin.userId)
        .map((admin) => admin && admin.userId)
        .filter((id): id is string => id !== null && id !== undefined);
      setAdminIds(ids);
    }
  }, [adminDataResult]);

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
    columns.push({
      header: "Admin",
      accessorKey: "isAdmin",
      sortingFn: (rowA, rowB) => {
        const isAdminA = adminIds.includes(rowA.original.id);
        const isAdminB = adminIds.includes(rowB.original.id);
        return isAdminA === isAdminB ? 0 : isAdminA ? -1 : 1;
      },
      cell: ({ row }) => {
        const isAdmin = adminIds.includes(row.original.id);
        return isAdmin ? "Yes" : "No";
      },
    });
  }

  columns.push({
    header: "Actions",
    accessorKey: "actions",
    cell: ({ row }) => (
      <UserActions
        user={row.original}
        isAdmin={adminIds.includes(row.original.id)}
      />
    ),
  });

  if (loading) {
    return <LoadingSpinner />;
  }

  return <Table data={users} columns={columns} />;
}

export default UserTable;

function UserActions({ user, isAdmin }: { user: User; isAdmin: boolean }) {
  const { hasPermission } = useAdminPermissionsStore();

  const [deactivateUserMutation] = useMutation(DEACTIVATE_USER_MUTATION, {
    refetchQueries: [{ query: GET_ALL_USERS }, { query: GET_ADMIN_IDS }],
  });

  const [createAdmin] = useMutation(CREATE_ADMIN_MUTATION, {
    refetchQueries: [{ query: GET_ALL_USERS }, { query: GET_ADMIN_IDS }],
  });

  let options = [];
  if (!isAdmin && hasPermission(Resources.Admins, Actions.Create)) {
    options.push({
      label: "Make Admin",
      onClick: () => {
        addUserToAdminGroup({ id: user.id });
        createAdmin({ variables: { userId: user.id } });
      },
    });
  }

  if (hasPermission(Resources.Users, Actions.Write)) {
    options.push({
      label: "Deactivate User",
      onClick: () => {
        deactivateUserMutation({ variables: { id: user.id } });
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
