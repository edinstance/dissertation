-- This function creates an admin with a default role.
CREATE OR REPLACE FUNCTION create_admin(
    _user_id UUID,
    _admin_id UUID
)
RETURNS TABLE (
    user_id UUID,
    is_super_admin BOOLEAN,
    status VARCHAR,
    created_by UUID,
    last_updated_by UUID,
    is_deleted BOOLEAN
) AS $$
DECLARE
    _role_id UUID;
    new_admin RECORD;
BEGIN    
    
    PERFORM check_user_and_admin_status(_user_id, _admin_id);

    INSERT INTO admins(
        user_id,
        is_super_admin,
        status,
        created_by,
        last_updated_by,
        is_deleted
    )
    VALUES (
        _user_id,
        FALSE,
        'ACTIVE',
        _admin_id,
        _admin_id,
        FALSE
    )
    RETURNING * INTO new_admin;

    SELECT role_id INTO _role_id
    FROM roles
    WHERE role_name = 'Default Admin';

    IF _role_id IS NULL THEN
        RAISE EXCEPTION 'Default Admin role does not exist';
    END IF;

    INSERT INTO admin_roles (
        admin_id,
        role_id
    ) VALUES (_user_id, _role_id);

    RETURN QUERY
    SELECT
        new_admin.user_id,
        new_admin.is_super_admin,
        new_admin.status,
        new_admin.created_by,
        new_admin.last_updated_by,
        new_admin.is_deleted;
END;
$$ LANGUAGE plpgsql;
