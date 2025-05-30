"use client";

import { GET_USER_BILLING } from "@/lib/graphql/users";
import { cn } from "@/lib/utils";
import { useQuery } from "@apollo/client";
import {
  PlusIcon,
  ShoppingBagIcon,
  ShoppingCartIcon,
} from "@heroicons/react/24/outline";
import Link from "next/link";
import { usePathname } from "next/navigation";

const sidebarLinks = [
  {
    name: "Shop",
    href: "/shop",
    icon: ShoppingCartIcon,
  },
  {
    name: "Items",
    href: "/items",
    icon: PlusIcon,
  },
  {
    name: "Won Items",
    href: "/items/won",
    icon: ShoppingBagIcon,
  },
];

/**
 * Sidebar component for navigation.
 *
 * This component renders a vertical sidebar with navigation links.
 * Each link is represented by an icon and highlights when active.
 *
 * @returns The rendered sidebar component.
 */
export function Sidebar() {
  const pathname = usePathname();

  const userBilling = useQuery(GET_USER_BILLING);
  const accountId = userBilling.data?.getUserBilling?.accountId;

  // Filter out the Items page from sidebar links if no account ID exists
  const filteredSidebarLinks = sidebarLinks.filter(
    (link) => link.name !== "Items" || accountId,
  );

  return (
    <div className="fixed left-0 top-16 z-50 h-full w-16 border-r border-gray-200 bg-white dark:border-gray-800 dark:bg-zinc-900">
      <div className="flex h-full flex-col items-center space-y-4 py-4">
        {filteredSidebarLinks.map((link) => (
          <Link
            key={link.name}
            href={link.href}
            className={cn(
              "flex h-12 w-12 items-center justify-center rounded-lg text-gray-500 transition-colors hover:bg-gray-100 hover:text-gray-900 dark:text-gray-400 dark:hover:bg-gray-800 dark:hover:text-gray-100",
              pathname === link.href &&
                "bg-gray-100 text-gray-900 dark:bg-gray-800 dark:text-gray-100",
            )}
            title={link.name}
          >
            <link.icon className="h-6 w-6" />
          </Link>
        ))}
      </div>
    </div>
  );
}
