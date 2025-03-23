-- This function gets admin permissions with an option to choose the view
CREATE OR REPLACE FUNCTION get_admin_permissions(
    view_type admin_permission_view_type DEFAULT 'ALL'
)
RETURNS TABLE (
    admin_id UUID,
    permission_id UUID,
    resource_id UUID,
    action_id UUID,
    grant_type VARCHAR,
    resource VARCHAR,
    action VARCHAR
) AS $$
BEGIN
    IF view_type = 'ALL' OR view_type IS NULL THEN
        RETURN QUERY SELECT * FROM consolidate_admin_permissions_view;
    ELSIF view_type = 'PERMISSIONS' THEN
        RETURN QUERY SELECT * FROM admin_permissions_view;
    ELSIF view_type = 'ROLES' THEN
        RETURN QUERY SELECT * FROM admin_role_permissions_view;
    ELSE
        RAISE EXCEPTION 'Invalid view_type. Valid options are: ALL, PERMISSIONS, ROLES';
    END IF;
END;
$$ LANGUAGE plpgsql;