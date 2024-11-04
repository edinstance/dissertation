"use client";

import { signIn, signOut, useSession } from "next-auth/react";
import { Button } from "./ui/Button";

export default function Header() {
  const session = useSession();
  return (
    <header className="fixed left-0 right-0 top-0 z-50 flex h-16">
      <div className="flex w-full flex-row items-center justify-between bg-zinc-200 px-16 dark:bg-zinc-950">
        <p className="text-black dark:text-white">Final Year Project</p>
        <div>
          {session.status == "authenticated" ? (
            <Button
              onClick={() => {
                signOut();
              }}
            >
              Sign out
            </Button>
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
