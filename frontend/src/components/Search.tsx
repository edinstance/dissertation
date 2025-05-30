"use client";

import { useSearchStore } from "@/stores/SearchStore";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import debounce from "lodash.debounce";
import { useEffect, useState } from "react";
import { Input } from "./ui/Input";

/**
 * SearchBar component for searching items.
 *
 * This component renders a search input field with a magnifying glass icon.
 * It allows users to input a search query and triggers a search when the form is submitted.
 *
 * @param props - The props for the component.
 * @returns The rendered SearchBar component.
 */
export function SearchBar({
  placeholder = "Search...",
  className = "",
}: {
  placeholder?: string;
  className?: string;
}) {
  const [isFocused, setIsFocused] = useState(false);
  const { searchQuery, setSearchQuery, setDebouncedQuery, setCurrentPage } =
    useSearchStore();

  const debouncedSearch = debounce((query: string) => {
    setDebouncedQuery(query);
  }, 300);

  useEffect(() => {
    debouncedSearch(searchQuery);
    return () => debouncedSearch.cancel();
  }, [searchQuery, debouncedSearch]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
    setCurrentPage(0);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    setDebouncedQuery(searchQuery);
  };

  return (
    <form className={`w-full max-w-md ${className}`} onSubmit={handleSubmit}>
      <div className="relative">
        <div
          className={`pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3 transition-opacity duration-200 ${
            isFocused ? "opacity-0" : "opacity-100"
          } ${isFocused ? "text-blue-500" : "text-gray-500"}`}
        >
          <MagnifyingGlassIcon className="h-5 w-5" aria-hidden="true" />
        </div>
        <Input
          type="text"
          value={searchQuery}
          onChange={handleInputChange}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          placeholder={placeholder}
          className={`w-full rounded-lg border border-gray-300 bg-white text-sm text-gray-900 transition-all duration-200 ease-in-out focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-800 dark:text-white dark:placeholder-gray-400 dark:focus:border-blue-500 ${
            isFocused ? "pl-4" : "pl-10"
          } py-2 pr-4`}
        />
        {searchQuery && (
          <button
            type="button"
            onClick={() => setSearchQuery("")}
            className="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-500 hover:text-gray-700 dark:hover:text-gray-300"
          >
            <span className="text-xl">&times;</span>
          </button>
        )}
      </div>
    </form>
  );
}
