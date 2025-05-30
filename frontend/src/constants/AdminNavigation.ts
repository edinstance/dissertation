import { Actions, Resources } from "@/gql/graphql";

export const ADMIN_NAVIGATION_LINKS = [
  {
    label: "Users",
    link: "/admin/users",
    resource: Resources.Users,
    action: Actions.Read,
  },
  {
    label: "Admins",
    link: "/admin/admins",
    resource: Resources.Admins,
    action: Actions.Read,
  },
  {
    label: "Permissions",
    link: "/admin/permissions",
    resource: Resources.Permissions,
    action: Actions.Read,
  },
];
