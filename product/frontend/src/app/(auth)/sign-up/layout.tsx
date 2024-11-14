import "@/styles/globals.css";

import { ApolloWrapper } from "@/components/apollo";
import { auth } from "@/server/auth";

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";
const BACKEND_API_KEY = process.env.API_KEY ?? "";

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const session = await auth();

  return (
    <html lang="en">
      <body className={`bg-zinc-100 dark:bg-zinc-900`}>
        <ApolloWrapper link={BACKEND_GRAPHQL_ENDPOINT} apiKey={BACKEND_API_KEY}>
          {children}
        </ApolloWrapper>
      </body>
    </html>
  );
}
