import "@/styles/globals.css";

import { ApolloWrapper } from "@/components/apollo";

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";
const BACKEND_API_KEY = process.env.API_KEY ?? "";

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <ApolloWrapper link={BACKEND_GRAPHQL_ENDPOINT} apiKey={BACKEND_API_KEY}>
      {children}
    </ApolloWrapper>
  );
}
