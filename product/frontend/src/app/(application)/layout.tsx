import { ApolloWrapper } from "@/components/apollo";
import { Sidebar } from "@/components/ui/Sidebar";
import { auth } from "@/server/auth";
import { redirect } from "next/navigation";

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";

/**
 * RootLayout component for the application.
 *
 * This component serves as the main layout for the application, wrapping
 * the content with the ApolloWrapper to provide GraphQL functionality.
 * It checks for user authentication and redirects to a not-found page if
 * the user is not authenticated. It also includes a sidebar for navigation.
 *
 * @param props - The props for the component.
 * @returns A promise that resolves to the rendered RootLayout component.
 */
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
      <Sidebar />
      <div className="pl-16">{children}</div>
    </ApolloWrapper>
  );
}
