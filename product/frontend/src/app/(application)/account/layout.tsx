import UserNavigation from "@/components/Users/UserNavigation";

export default function Layout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <div className="px-16 pt-20">
      <UserNavigation />
      {children}
    </div>
  );
}
