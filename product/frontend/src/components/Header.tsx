"use client";

import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { UserCircleIcon } from "@heroicons/react/24/outline";
import { signIn, signOut, useSession } from "next-auth/react";
import Link from "next/link";
import { Button } from "./ui/Button";

export default function Header({ launched }: { launched: boolean }) {
  const session = useSession();
  return (
    <header className="fixed left-0 right-0 top-0 z-40 flex h-16">
      <div className="flex w-full flex-row items-center justify-between bg-zinc-200 px-16 text-black dark:bg-zinc-950 dark:text-white">
        <Link href="/" className="text-3xl font-bold">
          SubShop
        </Link>
        <div>
          {session.status == "authenticated" ? (
            <div className="flex flex-row rounded-lg text-zinc-700 hover:bg-zinc-300 dark:text-zinc-200 dark:hover:bg-zinc-600">
              <Menu>
                <MenuButton>
                  <div className="flex flex-row items-center space-x-1 px-2">
                    <p>{session?.data?.user?.name}</p>
                    <UserCircleIcon className="h-8 w-8 rounded-md" />
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
            </div>
          ) : (
            launched && (
              <div className="flex flex-row items-center space-x-4">
                <Button
                  onClick={() => {
                    signIn();
                  }}
                >
                  Sign In
                </Button>
                <Button href="/sign-up" variant="outline">
                  Sign up
                </Button>
              </div>
            )
          )}
        </div>
      </div>
    </header>
  );
}
