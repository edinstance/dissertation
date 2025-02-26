"use client";
import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { UserCircleIcon } from "@heroicons/react/24/outline";
import { signOut, useSession } from "next-auth/react";

/**
 * UserMenu component for displaying a user menu with account options.
 *
 * This component renders a button that shows the user's name and an icon.
 * When clicked, it displays a dropdown menu with options to navigate to the account page
 * and to sign out of the session.
 *
 * @returns The rendered UserMenu component.
 */
export function UserMenu() {
  const session = useSession();
  return (
    <Menu>
      <MenuButton>
        <div className="flex flex-row items-center space-x-1">
          <p className="hidden md:block">{session?.data?.user?.name}</p>
          <UserCircleIcon className="h-6 w-6" />
        </div>
      </MenuButton>
      <MenuItems
        anchor="bottom end"
        transition
        className="absolute right-0 z-50 mt-2 w-48 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none dark:bg-zinc-800"
      >
        <MenuItem>
          <a
            className="block w-full rounded-md bg-white px-4 py-2 text-left text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-offset-2 dark:bg-zinc-800 dark:text-white dark:hover:bg-zinc-700 dark:focus:ring-offset-zinc-900"
            href="/account"
          >
            Account
          </a>
        </MenuItem>
        <MenuItem>
          <button
            onClick={() => {
              signOut({ redirect: true, redirectTo: "/" });
            }}
            className="block w-full rounded-md bg-white px-4 py-2 text-left text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-100 focus:outline-none focus:ring-2 *:focus:ring-offset-2 dark:bg-zinc-800 dark:text-white dark:hover:bg-zinc-700 dark:focus:ring-offset-zinc-900"
          >
            Sign Out
          </button>
        </MenuItem>
      </MenuItems>
    </Menu>
  );
}
