import { auth } from "@/server/auth";
import { redirect } from "next/navigation";

export default async function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const session = await auth();

  console.log(session);

  if (!session || !session?.user?.groups?.includes("SubShopAdmin")) {
    redirect("/not-found");
  }


  return children ;
}
