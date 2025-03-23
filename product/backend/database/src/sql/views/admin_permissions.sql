-- This view consolidates the admin permissions
CREATE OR REPLACE VIEW admin_permissions_view AS
SELECT
    ap.admin_id,
    p.permission_id,
    r.resource_id,
    a.action_id,
    ap.grant_type,
    r.resource,
    a.action
FROM
    admin_permissions ap
JOIN
    permissions p ON ap.permission_id = p.permission_id
JOIN
    resources r ON p.resource_id = r.resource_id
JOIN
    actions a ON p.action_id = a.action_id;