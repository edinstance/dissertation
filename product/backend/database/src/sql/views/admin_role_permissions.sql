-- This view consolidates the admin role permissions
CREATE OR REPLACE VIEW admin_role_permissions_view AS
SELECT
    ar.admin_id,
    p.permission_id,
    r.resource_id,
    a.action_id,
    rp.grant_type,
    r.resource,
    a.action
FROM
    admin_roles ar
JOIN
    role_permissions rp ON ar.role_id = rp.role_id
JOIN
    permissions p ON rp.permission_id = p.permission_id
JOIN
    resources r ON p.resource_id = r.resource_id
JOIN
    actions a ON p.action_id = a.action_id;