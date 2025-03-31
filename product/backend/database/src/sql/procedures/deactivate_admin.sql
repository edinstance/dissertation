-- This procedure deactivates an admin.
CREATE OR REPLACE PROCEDURE deactivate_admin (
    _user_id UUID,
    _admin_id UUID
) AS $$
BEGIN
    PERFORM check_user_and_admin_status(_user_id, _admin_id);

    DELETE FROM admin_roles
    WHERE admin_id = _admin_id;

    DELETE FROM admin_permissions
    WHERE admin_id = _admin_id;

    UPDATE admins
    SET
        status = 'DEACTIVATED',
        is_super_admin = FALSE,
        last_updated_by = _admin_id
    WHERE
        user_id = _user_id;
END;
$$ LANGUAGE plpgsql;