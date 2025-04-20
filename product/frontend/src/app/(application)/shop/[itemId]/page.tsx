"use client";

import BidSidebar from "@/components/Bids/BidSidebar";
import ChatWindow from "@/components/Chat/Window";
import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import { IS_CHAT_ENABLED } from "@/lib/graphql/chats";
import { GET_ITEM_BY_ID_QUERY } from "@/lib/graphql/items";
import { useQuery } from "@apollo/client";
import Image from "next/image";
import { useParams } from "next/navigation";
import { useState } from "react";

export default function ItemPage() {
  const params = useParams();
  const itemId = params.itemId as string;

  const [sidebarOpen, setSidebarOpen] = useState(false);

  const { loading, data } = useQuery(GET_ITEM_BY_ID_QUERY, {
    variables: {
      id: itemId,
    },
  });

  const item = data?.getItemById;

  const isChatEnabledQuery = useQuery(IS_CHAT_ENABLED);
  const isChatEnabled = isChatEnabledQuery.data?.isChatEnabled;

  return (
    <div className="flex h-screen flex-col bg-zinc-100 dark:bg-zinc-900">
      <div className="fixed left-16 right-0 top-16 z-50 flex flex-row border-b bg-zinc-100 px-4 py-4 pl-4 dark:border-zinc-800 dark:bg-zinc-900">
        <Button
          variant="outline"
          className="flex items-center gap-2"
          href="/shop"
        >
          Back
        </Button>
        <h1 className="flex-grow text-center text-xl font-bold text-black dark:text-white md:text-2xl">
          {item?.name ?? "Item"}
        </h1>
        <Button
          variant="outline"
          className="md:hidden"
          onClick={() => setSidebarOpen(!sidebarOpen)}
          aria-controls="bidding-sidebar"
          aria-expanded={sidebarOpen}
        >
          {sidebarOpen ? "Close" : "Bidding"}
        </Button>
      </div>

      <div className="relative flex flex-1 overflow-hidden pt-32">
        <div className="flex-1 overflow-y-auto p-4 md:p-6">
          {loading && <LoadingSpinner />}
          {item && (
            <div className="mx-auto max-w-4xl">
              {item.images && item.images.length > 0 && (
                <div className="mb-6 flex w-full flex-nowrap gap-4 overflow-x-auto rounded-lg bg-white p-4 shadow-md dark:bg-zinc-800">
                  {item.images
                    .filter(
                      (image): image is string => typeof image === "string",
                    )
                    .map((image, index) => (
                      <div key={index} className="flex-shrink-0">
                        <Image
                          src={image}
                          width={256}
                          height={256}
                          alt={`${item.name ?? "Item"} image ${index + 1}`}
                          className="h-64 w-64 rounded-lg object-cover transition-transform duration-300 hover:scale-105"
                          loading="lazy"
                        />
                      </div>
                    ))}
                </div>
              )}

              <div className="mb-6 grid grid-cols-1 gap-4 rounded-lg bg-white p-6 text-lg shadow-md dark:bg-zinc-800 dark:text-gray-300 sm:grid-cols-2">
                <p>
                  <span className="font-semibold">Category: </span>
                  {item.category ?? "N/A"}
                </p>
                <p>
                  <span className="font-semibold">Stock: </span>
                  {item.stock ?? "N/A"}
                </p>
              </div>

              <div className="rounded-lg bg-white p-6 shadow-md dark:bg-zinc-800 dark:text-gray-300">
                <p className="mb-2 text-xl font-semibold">Description:</p>
                <p className="pl-4 text-base">
                  {item.description ?? "No description available."}
                </p>
              </div>
            </div>
          )}
          {!loading && !item && (
            <p className="text-center text-gray-500">Item not found.</p>
          )}
        </div>

        {sidebarOpen && (
          <div
            className="fixed inset-0 z-30 bg-black/50 transition-opacity duration-300 ease-in-out md:hidden"
            onClick={() => setSidebarOpen(false)}
            aria-hidden="true"
          />
        )}
        {item && <BidSidebar isOpen={sidebarOpen} item={item} />}
      </div>
      {isChatEnabled && !isChatEnabledQuery.loading && <ChatWindow />}
    </div>
  );
}
