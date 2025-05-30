import AdminWrapper from "@/components/Admin/AdminWrapper";
import TabNavigation from "@/components/ui/TabNavigation";
import { getAdminNavigationLinks } from "@/lib/admin/getAdminNavLinks";
import { auth } from "@/server/auth";
import { redirect } from "next/navigation";

export default async function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const session = await auth();

  if (!session || !session?.user?.groups?.includes("SubShopAdmin")) {
    redirect("/not-found");
  }
  console.log("AdminLayout session", session);

  return (
    <AdminWrapper>
      <div className="pl-8 pr-16 pt-20">
        <div className="pb-8">
          <TabNavigation getLinks={getAdminNavigationLinks} />
        </div>
        {children}
      </div>
    </AdminWrapper>
  );
}
