-- The function returns all permissions of a admin by their id.
CREATE OR REPLACE FUNCTION get_admin_permissions_by_id(
    _user_id UUID,
    view_type admin_permission_view_type DEFAULT 'ALL'
)
RETURNS TABLE (
    user_id UUID,
    permission_id UUID,
    resource_id UUID,
    action_id UUID,
    grant_type VARCHAR,
    resource VARCHAR,
    action VARCHAR
) AS $$
BEGIN
   RETURN QUERY
    SELECT
        p.user_id,
        p.permission_id,
        p.resource_id,
        p.action_id,
        p.grant_type,
        p.resource,
        p.action
    FROM get_all_admin_permissions(view_type) AS p
    WHERE p.user_id = _user_id;
END;
$$ LANGUAGE plpgsql;
