"use client";

import { signIn } from "next-auth/react";

export default function HomePage() {
  return (
    <main className="flex min-h-screen items-center justify-center bg-zinc-100 text-black dark:bg-zinc-900 dark:text-white">
      <div className="flex flex-col">
        <button
          onClick={() => {
            signIn();
          }}
        >
          Login
        </button>
      </div>
    </main>
  );
}
