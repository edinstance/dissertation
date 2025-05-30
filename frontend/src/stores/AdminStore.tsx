import {
  Actions,
  Admin,
  GrantType,
  PermissionView,
  PermissionViewId,
  Resources,
} from "@/gql/graphql";
import { create } from "zustand";
import { createJSONStorage, devtools, persist } from "zustand/middleware";

interface AdminPermissionsStore {
  currentAdmin: Admin | null;
  adminPermissions: PermissionView[];

  setCurrentAdmin: (admin: Admin) => void;
  clearCurrentAdmin: () => void;

  setAdminPermissions: (permissions: PermissionView[]) => void;
  addAdminPermission: (permission: PermissionView) => void;
  removeAdminPermission: (permissionId: PermissionViewId) => void;

  hasPermission: (resource: Resources, action: Actions) => boolean;
  isCurrentAdminSuperAdmin: () => boolean;
}

const useAdminPermissionsStore = create<AdminPermissionsStore>()(
  devtools(
    persist(
      (set, get) => ({
        currentAdmin: null,
        adminPermissions: [],

        setCurrentAdmin: (admin) => set({ currentAdmin: admin }),

        clearCurrentAdmin: () =>
          set({ currentAdmin: null, adminPermissions: [] }),

        setAdminPermissions: (permissions) =>
          set({ adminPermissions: permissions }),

        addAdminPermission: (permission) =>
          set((state) => ({
            adminPermissions: [...state.adminPermissions, permission],
          })),

        removeAdminPermission: (permissionId) =>
          set((state) => ({
            adminPermissions: state.adminPermissions.filter(
              (p) =>
                !(
                  p.id.userId === permissionId.userId &&
                  p.id.permissionId === permissionId.permissionId &&
                  p.id.resourceId === permissionId.resourceId &&
                  p.id.actionId === permissionId.actionId
                ),
            ),
          })),

        hasPermission: (resource, action) => {
          const { currentAdmin, adminPermissions } = get();
          if (currentAdmin?.isSuperAdmin) return true;

          const relevantPermissions = adminPermissions.filter(
            (p) => p.resource === resource && p.action === action,
          );

          if (relevantPermissions.length === 0) return false;

          return relevantPermissions.some(
            (p) => p.grantType === GrantType.Grant,
          );
        },

        isCurrentAdminSuperAdmin: () => {
          const { currentAdmin } = get();
          return !!currentAdmin?.isSuperAdmin;
        },
      }),
      {
        name: "admin-permissions-storage",
        storage: createJSONStorage(() => localStorage),
        partialize: (state) => ({
          currentAdmin: state.currentAdmin,
          adminPermissions: state.adminPermissions,
        }),
      },
    ),
    { name: "AdminPermissionsStore" },
  ),
);

export default useAdminPermissionsStore;
