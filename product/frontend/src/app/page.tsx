"use client";

import { GET_SHOWS_QUERY } from "@/lib/graphql";
import { useQuery } from "@apollo/client";
import { signIn } from "next-auth/react";

export default function HomePage() {
  const { data: queryData, loading: queryLoading } = useQuery(GET_SHOWS_QUERY, {
    onCompleted(data) {
      console.log("account data", data);
    },
    onError(error) {
      console.error("query error", error.message);
    },
  });

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
        <h1 className="text-4xl font-bold">Shows</h1>
        {queryLoading ? (
          <p>Loading...</p>
        ) : (
          queryData?.shows && (
            <ul>
              {queryData?.shows.map((show) => (
                <li key={show?.title}>{show?.title}</li>
              ))}
            </ul>
          )
        )}
      </div>
    </main>
  );
}
