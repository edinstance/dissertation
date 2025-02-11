import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

/**
 * Combines class names using clsx and merges Tailwind CSS classes using tailwind-merge.
 *
 * @param inputs - The class names to combine. Can be strings, objects, or arrays.
 * @returns The merged class names as a single string.
 */
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
