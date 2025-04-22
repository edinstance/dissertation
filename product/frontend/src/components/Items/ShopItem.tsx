"use client";

import BidSidebar from "@/components/Bids/BidSidebar";
import { Button } from "@/components/ui/Button";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import { GET_ITEM_BY_ID_QUERY } from "@/lib/graphql/items";
import { GET_USER_BILLING } from "@/lib/graphql/users";
import { useQuery } from "@apollo/client";
import Image from "next/image";
import { useEffect, useState } from "react";

function ShopItem({
  itemId,
  stripeKey,
}: {
  itemId: string;
  stripeKey: string;
}) {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const { data: customerInfo, loading: customerLoading } =
    useQuery(GET_USER_BILLING);

  const [bidderStripeCustomerId, setBidderStripeCustomerId] = useState<
    string | null
  >(null);

  useEffect(() => {
    if (customerInfo?.getUserBilling?.customerId) {
      setBidderStripeCustomerId(customerInfo.getUserBilling.customerId);
    }
  }, [customerInfo]);

  const {
    loading: itemLoading,
    data: itemData,
    error: itemError,
  } = useQuery(GET_ITEM_BY_ID_QUERY, {
    variables: {
      id: itemId,
    },
    onError: (error) => {
      console.error("Error fetching item:", error);
      setErrorMessage("Could not load item details.");
    },
  });

  const loading = itemLoading || customerLoading;
  const item = itemData?.getItemById;

  useEffect(() => {
    if (itemError) {
      setErrorMessage("Could not load item details.");
    }
  }, [itemError]);

  return (
    <div className="flex h-screen flex-col bg-zinc-100 dark:bg-zinc-900">
      <div className="fixed left-0 right-0 top-16 z-50 flex flex-row items-center border-b bg-zinc-100 px-4 py-4 dark:border-zinc-800 dark:bg-zinc-900 md:left-16">
        <Button
          variant="outline"
          className="flex items-center gap-2"
          href="/shop"
        >
          Back
        </Button>
        <h1 className="flex-grow text-center text-xl font-bold text-black dark:text-white md:text-2xl">
          {item?.name ?? (loading ? "Loading..." : "Item")}
        </h1>
        <div className="w-auto md:hidden">
          <Button
            variant="outline"
            onClick={() => setSidebarOpen(!sidebarOpen)}
            aria-controls="bidding-sidebar"
            aria-expanded={sidebarOpen}
          >
            {sidebarOpen ? "Close Bids" : "Show Bids"}
          </Button>
        </div>
      </div>

      <div className="relative flex flex-1 overflow-hidden pt-32">
        <div className="flex-1 overflow-y-auto p-4 md:p-6">
          {loading && <LoadingSpinner />}
          {errorMessage && !loading && (
            <p className="mb-4 text-center text-red-600">{errorMessage}</p>
          )}
          {item && !loading && (
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

              <div className="mb-6 rounded-lg bg-white p-6 shadow-md dark:bg-zinc-800 dark:text-gray-300">
                <p className="mb-2 text-xl font-semibold">Description:</p>
                <p className="whitespace-pre-wrap pl-4 text-base">
                  {item.description ?? "No description available."}
                </p>
              </div>
            </div>
          )}
          {!loading && !item && !errorMessage && (
            <p className="text-center text-gray-500">Item not found.</p>
          )}
        </div>

        {item && (
          <BidSidebar
            isOpen={sidebarOpen}
            item={item}
            stripeKey={stripeKey}
            bidderStripeCustomerId={bidderStripeCustomerId}
          />
        )}
      </div>
    </div>
  );
}

export default ShopItem;
