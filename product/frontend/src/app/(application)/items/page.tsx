"use client";
import ItemOverviewGrid from "@/components/Items/ItemOverviewGrid";
import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import {
  GetItemsByUserQuery,
  GetItemsByUserQueryVariables,
  Item,
} from "@/gql/graphql";
import { GET_ITEMS_BY_USER_QUERY } from "@/lib/graphql/items";
import { useQuery } from "@apollo/client";
import { useSession } from "next-auth/react";
import { useState } from "react";

export default function Items() {
  const session = useSession();

  const [items, setItems] = useState<Item[]>([]);

  const { loading } = useQuery<
    GetItemsByUserQuery,
    GetItemsByUserQueryVariables
  >(GET_ITEMS_BY_USER_QUERY, {
    variables: { id: session.data?.user?.id, isActive: true },
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
      }
    },
  });

  return (
    <div className="px-8 pt-20">
      <div className="flex flex-row items-center justify-between pb-4">
        <h1 className="text-4xl">Mange your Items</h1>
        <Button className="mt-4" href={"items/createItem"}>
          Create a new Item
        </Button>
      </div>
      <div>
        {loading ? (
          <div className="flex min-h-screen items-center justify-center pt-20">
            <div className="text-center">
              <LoadingSpinner />
            </div>
          </div>
        ) : items.length !== 0 ? (
          <ItemOverviewGrid items={items} />
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
