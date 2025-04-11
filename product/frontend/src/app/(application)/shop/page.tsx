"use client";

import ChatWindow from "@/components/Chat/Window";
import ItemShopCard from "@/components/Items/ItemShopCard";
import ItemSorting from "@/components/Items/ItemSorting";
import { SearchBar } from "@/components/Search";
import LoadingSpinner from "@/components/ui/LoadingSpinner";
import Pagination from "@/components/ui/Pagination";
import { Item, SortDirection, Sorting } from "@/gql/graphql";
import { GET_SHOP_ITEMS, SEARCH_FOR_ITEMS } from "@/lib/graphql/items";
import { useSearchStore } from "@/stores/SearchStore";
import { useLazyQuery } from "@apollo/client";
import { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";

/**
 * Shop component for displaying the shop page.
 *
 * This component renders a search bar, search results, and pagination.
 *
 * @returns The rendered Shop component.
 */
export default function Shop() {
  const { debouncedQuery, currentPage, setCurrentPage } = useSearchStore();
  const [
    executeSearch,
    { data: searchItemsData, loading: searchItemsLoading },
  ] = useLazyQuery(SEARCH_FOR_ITEMS);
  const [getShopItems, { data: shopItemsData, loading: shopItemsLoading }] =
    useLazyQuery(GET_SHOP_ITEMS);

  const [sorting, setSorting] = useState<Sorting>({
    sortBy: "ending_time",
    sortDirection: SortDirection.Asc,
  });

  const isSearchMode = debouncedQuery.trim().length > 0;
  const loading = isSearchMode ? searchItemsLoading : shopItemsLoading;

  const getResults = () => {
    if (isSearchMode && searchItemsData?.searchForItems) {
      return searchItemsData.searchForItems;
    } else if (!isSearchMode && shopItemsData?.getShopItems) {
      return shopItemsData.getShopItems;
    }
    return null;
  };

  const results = getResults();

  useEffect(() => {
    if (isSearchMode) {
      executeSearch({
        variables: {
          searchText: debouncedQuery,
          pagination: { page: currentPage, size: 9 },
          sorting: sorting,
        },
      });
    } else {
      getShopItems({
        variables: {
          pagination: { page: currentPage, size: 9 },
          sorting: sorting,
        },
      });
    }
  }, [
    debouncedQuery,
    currentPage,
    executeSearch,
    getShopItems,
    isSearchMode,
    sorting,
  ]);

  const hasResults = results?.items && results.items.length > 0;

  const filteredItems = results?.items
    ? results.items.filter((item): item is Item => item !== null)
    : [];

  const conversationId = useRef(uuidv4());

  return (
    <div className="flex min-h-screen flex-col bg-zinc-100 pt-16 dark:bg-zinc-900">
      <div className="fixed left-16 right-0 top-16 z-50 border-b bg-zinc-100 px-4 py-4 pl-20 dark:border-zinc-800 dark:bg-zinc-900 sm:pl-4">
        <div className="mx-auto flex max-w-4xl flex-row justify-between space-x-4 sm:pl-16">
          <SearchBar
            placeholder="Search for items..."
            className="w-full max-w-full"
          />
          <div>
            <ItemSorting value={sorting} setItemSortValue={setSorting} />
          </div>
        </div>
      </div>

      <div className="relative max-w-7xl flex-grow overflow-hidden px-4 pt-24 text-black dark:text-white">
        {loading && <LoadingSpinner />}
        {hasResults ? (
          <ResultsList items={filteredItems} />
        ) : (
          !loading && <p>No results found.</p>
        )}
      </div>

      {results?.pagination && (
        <div className="mt-auto pb-4">
          <Pagination
            total={results?.pagination?.total ?? 0}
            page={currentPage}
            setCurrentPage={setCurrentPage}
          />
        </div>
      )}
      <ChatWindow conversationId={conversationId.current} />
    </div>
  );
}

/**
 * Component to render the list of search results.
 */
function ResultsList({ items }: { items: Item[] | null }) {
  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {items?.map((item, index) => <ItemShopCard key={index} item={item} />)}
    </div>
  );
}
