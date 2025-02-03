import { SearchItemsQuery } from "@/gql/graphql";
import { create } from "zustand";

interface SearchStore {
    searchQuery: string;
    results: SearchItemsQuery["searchForItems"];
    setSearchQuery: (query: string) => void;
    setSearchResults: (results: SearchItemsQuery["searchForItems"]) => void;
  }
  
  export const useSearchStore = create<SearchStore>((set) => ({
    searchQuery: "",
    results: [],
    setSearchQuery: (searchQuery) => set({ searchQuery }),
    setSearchResults: (results) => set({ results }),
  }));