"use client";

import { UserCircleIcon } from "@heroicons/react/24/outline";
import { signIn, signOut, useSession } from "next-auth/react";
import { Button } from "./ui/Button";

export default function Header() {
  const session = useSession();
  return (
    <header className="fixed left-0 right-0 top-0 z-50 flex h-16">
      <div className="flex w-full flex-row items-center justify-between bg-zinc-200 px-16 text-black dark:bg-zinc-950 dark:text-white">
        <p>Final Year Project</p>
        <div>
          {session.status == "authenticated" ? (
            <div className="flex flex-row items-center space-x-4">
              <Button
                onClick={() => {
                  signOut({ callbackUrl: "/" });
                }}
              >
                Sign out
              </Button>
              <UserCircleIcon className="h-12 w-12" />
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
