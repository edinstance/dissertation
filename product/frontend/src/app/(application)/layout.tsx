import { ApolloWrapper } from "@/components/apollo";
import { Sidebar } from "@/components/ui/Sidebar";
import { GET_USER } from "@/lib/graphql/users";
import { auth } from "@/server/auth";
import { GraphQLClient } from "graphql-request";
import { redirect } from "next/navigation";
import { Bounce, ToastContainer } from "react-toastify";

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

  let userStatus = null;

  // Fetch user status safely
  try {
    const client = new GraphQLClient(BACKEND_GRAPHQL_ENDPOINT, {
      headers: {
        Authorization: `Bearer ${session?.accessToken}`,
      },
    });

    const data = await client.request(GET_USER);
    userStatus = data?.getUser?.status;
  } catch (error) {
    console.error("Failed to fetch user", error);
    redirect("/not-found");
  }

  if (userStatus === "DEACTIVATED") {
    redirect("/deactivated");
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
