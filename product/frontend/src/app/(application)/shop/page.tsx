"use client";

import { SearchBar } from "@/components/Search";

export default function Shop() {
  return (
    <div className="min-h-screen bg-zinc-100 pt-16 dark:bg-zinc-900">
      <div className="z-35 fixed left-0 right-0 top-16 border-b bg-zinc-100/90 px-4 py-4 pl-20 backdrop-blur-lg dark:border-zinc-800 dark:bg-zinc-900/80 sm:pl-4">
        <div className="mx-auto max-w-4xl">
          <SearchBar
            placeholder="Search for items..."
            className="w-full max-w-full"
          />
        </div>
      </div>

      <div className="mx-auto max-w-7xl px-4 pt-24 text-black dark:text-white">
        <h1 className="text-4xl font-bold">Shop</h1>
      </div>
    </div>
  );
}
