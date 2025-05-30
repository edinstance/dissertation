-- This function creates an admin with a default role.
CREATE OR REPLACE PROCEDURE create_admin(
    _user_id UUID,
    _admin_id UUID
)
AS $$
DECLARE
    _role_id UUID;
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
    );

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
END;
$$ LANGUAGE plpgsql;
