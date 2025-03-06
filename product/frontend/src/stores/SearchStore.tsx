import { create } from "zustand";

interface SearchStore {
  searchQuery: string;
  debouncedQuery: string;
  currentPage: number;
  setSearchQuery: (query: string) => void;
  setDebouncedQuery: (query: string) => void;
  setCurrentPage: (page: number) => void;
}

export const useSearchStore = create<SearchStore>((set) => ({
  searchQuery: "",
  debouncedQuery: "",
  currentPage: 1,
  setSearchQuery: (searchQuery) => set({ searchQuery }),
  setDebouncedQuery: (debouncedQuery) => set({ debouncedQuery }),
  setCurrentPage: (currentPage) => set({ currentPage }),
}));
