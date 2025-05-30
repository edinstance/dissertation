import TabNavigation from "@/components/ui/TabNavigation";
import { USER_NAVIGATION_LINKS } from "@/constants/UserNavigation";

/**
 * Layout component for account pages.
 *
 * This component serves as a layout wrapper for account-related pages,
 * rendering the UserNavigation component and the child content.
 *
 * @param props - The props for the component.
 * @returns The rendered Layout component.
 */
export default function Layout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <div className="pl-8 pr-16 pt-20">
      <TabNavigation links={USER_NAVIGATION_LINKS} />
      {children}
    </div>
  );
}
