"use client";

import { GET_SHOWS_QUERY } from "@/lib/graphql";
import { useQuery } from "@apollo/client";

export default function Shop() {
  const { data: queryData, loading: queryLoading } = useQuery(GET_SHOWS_QUERY, {
    onCompleted(data) {
      console.log("account data", data);
    },
    onError(error) {
      console.error("query error", error.message);
    },
  });

  return (
    <div className="flex min-h-screen justify-center bg-zinc-100 pt-32 text-black dark:bg-zinc-900 dark:text-white">
      <div>
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
    </div>
  );
}
