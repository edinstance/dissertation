"use client";
import UserTable from "@/components/Admin/UserTable";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import { Actions, Resources } from "@/gql/graphql";
import { GET_ADMIN_IDS, GET_USER_STATS } from "@/lib/graphql/admin";
import useAdminPermissionsStore from "@/stores/AdminStore";
import { useQuery } from "@apollo/client";

export default function AdminUsers() {
  const { data, loading } = useQuery(GET_USER_STATS);

  const { hasPermission } = useAdminPermissionsStore();

  const userStats = data?.getUserStats;

  return (
    <div className="space-y-4">
      {loading ? (
        <LoadingSpinner />
      ) : hasPermission(Resources.Users, Actions.Read) ? (
        <div className="space-y-4">
          <div className="flex flex-col justify-between space-y-4 px-8 md:flex-row md:space-x-4 md:space-y-0">
            <TotalCard title="Total users:" value={userStats?.total ?? 0} />
            <TotalCard
              title="New users:"
              value={userStats?.newUserTotal ?? 0}
            />
            <TotalCard
              title="Deleted users:"
              value={userStats?.deletedUserTotal ?? 0}
            />
          </div>
          <UserTable />
        </div>
      ) : (
        <></>
      )}
    </div>
  );
}

function TotalCard({ title, value }: { title: string; value: number }) {
  return (
    <div className="flex w-full flex-row space-x-2 rounded-lg bg-zinc-200 p-4 dark:bg-zinc-800">
      <div>{title}</div>
      <div>{value}</div>
    </div>
  );
}
