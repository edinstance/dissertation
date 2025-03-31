-- This procedure revokes a specific permission from an admin.
CREATE OR REPLACE PROCEDURE revoke_permission_from_admin(
    _user_id UUID,
    _admin_id UUID,
    _permission_id UUID
) AS
$$
BEGIN
    PERFORM check_user_and_admin_status(_user_id, _admin_id);

    DELETE
    FROM admin_permissions
    WHERE admin_id = _user_id
      AND permission_id = _permission_id;

END;
$$ LANGUAGE plpgsql;