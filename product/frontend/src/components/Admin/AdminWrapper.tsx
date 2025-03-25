"use client";

import { useAdminInitialization } from "@/lib/hooks/initialiseAdminHook";

function AdminWrapper({ children }: { children: React.ReactNode }) {

  useAdminInitialization();

  return children;
}

export default AdminWrapper;