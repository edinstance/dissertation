"use client";

import { ThemeProvider as Theme } from "next-themes";

/**
 * ThemeProvider component for managing theme settings.
 *
 * This component wraps its children with the ThemeProvider from the `next-themes`
 * library, allowing for theme management based on user preferences or system settings.
 *
 * @param props - The props for the component.
 * @returns The rendered ThemeProvider component.
 */
export function ThemeProvider({ children }: { children: React.ReactNode }) {
  return (
    <Theme
      attribute="class"
      defaultTheme="system"
      enableSystem
      disableTransitionOnChange
    >
      {children}
    </Theme>
  );
}
