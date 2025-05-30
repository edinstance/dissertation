"use client";
import ItemOverviewGrid from "@/components/Items/ItemOverviewGrid";
import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import Pagination from "@/components/ui/Pagination";
import { Item, Pagination as PaginationType } from "@/gql/graphql";
import { GET_USERS_WON_ITEMS } from "@/lib/graphql/items";
import { useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";

export default function WonItems() {
  const session = useSession();

  const [items, setItems] = useState<Item[]>([]);
  const [pagination, setPagination] = useState<PaginationType>({
    total: 1,
    page: 0,
    size: 9,
  });

  const { loading, data, refetch } = useQuery(GET_USERS_WON_ITEMS, {
    variables: {
      pagination: { size: pagination?.size ?? 2, page: pagination?.page ?? 0 },
    },
    skip: !session.data?.user?.id,
  });

  useEffect(() => {
    if (data?.getUsersWonItems) {
      console.log("Won items data:", data);
      const wonItems = data.getUsersWonItems.items;

      if (wonItems) {
        const validItems = wonItems
          .filter((item): item is NonNullable<typeof item> => item !== null)
          .map((item) => ({
            id: item.id || "",
            name: item.name || "",
            description: item.description || "",
            isActive: item.isActive || false,
            stock: item.stock || 0,
            finalPrice: item.finalPrice || null,
          }));

        setItems(validItems);

        if (data.getUsersWonItems.pagination) {
          setPagination(data.getUsersWonItems.pagination);
        }
      }
    }
  }, [data]);

  useEffect(() => {
    if (session.data?.user?.id) {
      refetch({
        pagination: { size: pagination.size, page: pagination.page },
      });
    }
  }, [pagination.page, refetch, session.data?.user?.id, pagination.size]);

  const handlePageChange = (newPage: number) => {
    setPagination((prev) => ({ ...prev, page: newPage }));
  };

  return (
    <div className="px-8 pt-20">
      <div className="flex flex-col justify-between pb-4 md:flex-row">
        <h1 className="text-4xl">Your Won Items</h1>
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
            <ItemOverviewGrid items={items} isActive={false} />
            {pagination && (
              <Pagination
                total={pagination.total ?? 1}
                page={pagination.page ?? 0}
                setCurrentPage={handlePageChange}
              />
            )}
          </>
        ) : (
          <div className="flex flex-col items-center justify-center pt-8">
            <p>You have not won any items yet. Go buy some!</p>
            <Button className="mt-4" href={"/shop"}>
              Buy!
            </Button>
          </div>
        )}
      </div>
    </div>
  );
}
