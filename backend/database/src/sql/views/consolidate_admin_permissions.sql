-- This view consolidates the admin roles from their roles and permissions.
CREATE OR REPLACE VIEW consolidate_admin_permissions_view AS
SELECT *
FROM admin_permissions_view
UNION
SELECT *
FROM admin_role_permissions_view;