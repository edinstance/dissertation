"use client";
import { ADMIN_NAVIGATION_LINKS } from "@/constants/AdminNavigation";
import useAdminPermissionsStore from "@/stores/AdminStore";

export const getAdminNavigationLinks = () => {
  const adminStore = useAdminPermissionsStore.getState();
  const { hasPermission, isSuperAdmin } = adminStore;

  if (isSuperAdmin()) {
    let allLinks = ADMIN_NAVIGATION_LINKS.map(({ label, link }) => ({
      label,
      link,
    }));
    return [{ label: "Information", link: "/admin" }, ...allLinks];
  }

  let filteredLinks = ADMIN_NAVIGATION_LINKS.filter((item) =>
    hasPermission(item.resource, item.action),
  ).map(({ label, link }) => ({ label, link }));

  return [{ label: "Information", link: "/admin" }, ...filteredLinks];
};
