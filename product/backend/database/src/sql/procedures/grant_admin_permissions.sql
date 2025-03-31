-- This procedure grants an admin a permission
CREATE OR REPLACE PROCEDURE grant_admin_permission(
    _admin_id UUID,
    _performing_admin_id UUID,
    _permission_id UUID
) AS
$$
BEGIN
    PERFORM check_user_and_admin_status(_admin_id, _performing_admin_id);

    IF _permission_id IS NULL OR NOT EXISTS (
        SELECT 1
        FROM permissions
        WHERE permission_id = _permission_id
    ) THEN
        RAISE EXCEPTION 'Permission ID % is either NULL or does not exist in the permissions table.', _permission_id;
    END IF;


    INSERT INTO admin_permissions (admin_id, permission_id)
    VALUES (_admin_id, _permission_id)
    ON CONFLICT (admin_id, permission_id) DO NOTHING;

END;
$$ LANGUAGE plpgsql;