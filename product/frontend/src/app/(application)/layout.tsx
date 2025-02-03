import { ApolloWrapper } from "@/components/apollo";
import { auth } from "@/server/auth";
import { redirect } from "next/navigation";

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const session = await auth();

  if (!session) {
    redirect("/not-found");
  }

  return (
    <ApolloWrapper
      link={BACKEND_GRAPHQL_ENDPOINT}
      accessToken={session?.accessToken}
    >
      {children}
    </ApolloWrapper>
  );
}
