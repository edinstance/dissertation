"use client";

import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { UserCircleIcon } from "@heroicons/react/24/outline";
import { signIn, signOut, useSession } from "next-auth/react";
import { Button } from "./ui/Button";

export default function Header() {
  const session = useSession();
  return (
    <header className="fixed left-0 right-0 top-0 z-40 flex h-16">
      <div className="flex w-full flex-row items-center justify-between bg-zinc-200 px-16 text-black dark:bg-zinc-950 dark:text-white">
        <p>Final Year Project</p>
        <div>
          {session.status == "authenticated" ? (
            <div className="flex flex-row items-center space-x-4">
              <Menu>
                <MenuButton>
                  <UserCircleIcon className="h-8 w-8 rounded-md text-zinc-700 hover:bg-zinc-300 dark:text-zinc-200 dark:hover:bg-zinc-600" />
                </MenuButton>
                <MenuItems
                  anchor="bottom end"
                  transition
                  className="absolute right-0 z-50 mt-2 w-48 origin-top-right rounded-md bg-white dark:bg-zinc-800 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
                >
                  <MenuItem>
                    <a
                      className="block w-full px-4 py-2 text-left text-sm font-medium text-gray-700 bg-white rounded-md shadow-sm hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 dark:bg-zinc-800 dark:text-white dark:hover:bg-zinc-700 dark:focus:ring-offset-zinc-900"
                      href="/account"
                    >
                      Account
                    </a>
                  </MenuItem>
                  <MenuItem>
                    <button
                      onClick={() => {
                        signOut({ callbackUrl: "/" });
                      }}
                      className="block w-full px-4 py-2 text-left text-sm font-medium text-gray-700 bg-white rounded-md shadow-sm hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 dark:bg-zinc-800 dark:text-white dark:hover:bg-zinc-700 dark:focus:ring-offset-zinc-900"
                    >
                      Sign Out
                    </button>
                  </MenuItem>
                </MenuItems>
              </Menu>
            </div>
          ) : (
            <Button
              onClick={() => {
                signIn();
              }}
            >
              Sign In
            </Button>
          )}
        </div>
      </div>
    </header>
  );
}
