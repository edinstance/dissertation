"use client";

import { MoonIcon, SunIcon } from "@heroicons/react/24/outline";
import { useTheme } from "next-themes";
import { useEffect, useState } from "react";

/**
 * ThemeToggle component for switching between light and dark themes.
 *
 * This component renders a button that allows users to toggle between light
 * and dark themes. It uses the `next-themes` library to manage the theme state.
 *
 * @returns The rendered ThemeToggle component.
 */
export function ThemeToggle() {
  const [mounted, setMounted] = useState(false);
  const { resolvedTheme, setTheme } = useTheme();

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    <button
      className="rounded-lg p-2 hover:bg-zinc-300 dark:hover:bg-zinc-800"
      onClick={() => setTheme(resolvedTheme === "dark" ? "light" : "dark")}
    >
      {mounted &&
        (resolvedTheme === "dark" ? (
          <SunIcon className="h-6 w-6" />
        ) : (
          <MoonIcon className="h-6 w-6" />
        ))}
    </button>
  );
}
