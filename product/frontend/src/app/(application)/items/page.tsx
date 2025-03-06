"use client";
import ItemOverviewGrid from "@/components/Items/ItemOverviewGrid";
import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import Pagination from "@/components/ui/Pagination";
import Switch from "@/components/ui/Switch";
import {
  GetItemsByUserQuery,
  GetItemsByUserQueryVariables,
  Item,
  Pagination as PaginationType,
} from "@/gql/graphql";
import { GET_ITEMS_BY_USER_QUERY } from "@/lib/graphql/items";
import { useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";

export default function Items() {
  const session = useSession();

  const [items, setItems] = useState<Item[]>([]);
  const [itemsActive, setItemsActive] = useState(true);
  const [pagination, setPagination] = useState<PaginationType>({
    total: 1,
    page: 0,
    size: 9,
  });

  const { loading, refetch } = useQuery<
    GetItemsByUserQuery,
    GetItemsByUserQueryVariables
  >(GET_ITEMS_BY_USER_QUERY, {
    variables: {
      id: session.data?.user?.id,
      isActive: itemsActive,
      pagination: { size: pagination?.size ?? 2, page: pagination?.page ?? 0 },
    },
    skip: !session.data?.user?.id,
    onCompleted: (data) => {
      const items = data.getItemsByUser?.items;
      if (items) {
        const validItems = items
          .filter((item): item is NonNullable<typeof item> => item !== null)
          .map((item) => ({
            id: item.id || "",
            name: item.name || "",
            description: item.description || "",
            isActive: item.isActive || false,
            stock: item.stock || 0,
          }));

        setItems(validItems);
        setPagination(
          data.getItemsByUser?.pagination || {
            total: 1,
            page: 0,
            size: 9,
          },
        );
      }
    },
  });

  useEffect(() => {
    if (session.data?.user?.id) {
      refetch({
        id: session.data.user.id,
        isActive: itemsActive,
        pagination: { size: pagination.size, page: pagination.page },
      });
    }
  }, [
    pagination.page,
    refetch,
    session.data?.user?.id,
    pagination.size,
    itemsActive,
  ]);

  const handlePageChange = (newPage: number) => {
    setPagination((prev) => ({ ...prev, page: newPage }));
  };

  return (
    <div className="px-8 pt-20">
      <div className="flex flex-col justify-between pb-4 md:flex-row">
        <h1 className="text-4xl">Manage your Items</h1>
        <div className="flex items-center">
          <div className="flex items-center pr-2">
            <p className="mr-2">Active</p>
            <Switch checked={itemsActive} onCheckedChange={setItemsActive} />
          </div>

          <Button className="ml-4" href={"items/createItem"}>
            Create a new Item
          </Button>
        </div>
      </div>
      <div>
        {loading ? (
          <div className="flex min-h-screen items-center justify-center pt-20">
            <div className="text-center">
              <LoadingSpinner />
            </div>
          </div>
        ) : items.length !== 0 ? (
          <>
            <ItemOverviewGrid items={items} />
            {pagination && (
              <Pagination
                total={pagination.total ?? 1}
                page={pagination.page ?? 0}
                setCurrentPage={handlePageChange}
              />
            )}
          </>
        ) : !itemsActive && items.length === 0 ? (
          <div className="flex flex-col items-center justify-center pt-8">
            <p>All of your items are currently active</p>
            <Button className="mt-4" onClick={() => setItemsActive(true)}>
              Return to active items
            </Button>
          </div>
        ) : (
          <div className="flex flex-col items-center justify-center pt-8">
            <p>You have no items yet. Create a new one!</p>
            <Button className="mt-4" href={"items/createItem"}>
              Create a new Item
            </Button>
          </div>
        )}
      </div>
    </div>
  );
}
