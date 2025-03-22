-- Test procedure for create_admin function
CREATE OR REPLACE PROCEDURE test.test_create_admin()
AS $$
DECLARE
    admin_user_id   UUID;
    new_user_id     UUID;
    deleted_user_id UUID;
    default_role_id UUID;
    created_admin   RECORD;
    admin_count     INTEGER;
BEGIN
    -- Get the default admin role
    SELECT role_id
    INTO default_role_id
    FROM roles
    WHERE role_name = 'Default Admin';

    IF default_role_id IS NULL THEN
        RAISE EXCEPTION 'Default Admin role does not exist.';
    END IF;
   
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'admin@example.com', 'Test Admin')
    RETURNING user_id INTO admin_user_id;

    INSERT INTO admins (
        user_id,
        is_super_admin,
        status,
        created_by,
        last_updated_by,
        is_deleted
    ) VALUES (
        admin_user_id,
        TRUE,
        'ACTIVE',
        admin_user_id,
        admin_user_id,
        FALSE
    );

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'newUser@example.com', 'Test User')
    RETURNING users.user_id INTO new_user_id;

    SELECT * INTO created_admin FROM create_admin(new_user_id, admin_user_id);

    IF created_admin.user_id != new_user_id THEN
        RAISE EXCEPTION 'User ID mismatch: Expected %, got %', new_user_id, created_admin.user_id;
    END IF;

    SELECT COUNT(*)
    INTO admin_count
    FROM admins
    WHERE user_id = new_user_id;

    IF admin_count != 1 THEN
        RAISE EXCEPTION 'Admin not created';
    END IF;

    -- Check if all the guards are working
    BEGIN
        PERFORM create_admin(gen_random_uuid(), admin_user_id);
        RAISE EXCEPTION 'Admin created with invalid user ID';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'User with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    INSERT INTO users (user_id, email, name, is_deleted)
    VALUES (gen_random_uuid(), 'deleted@example.com', 'Deleted User', TRUE)
    RETURNING user_id INTO deleted_user_id;

    BEGIN
        PERFORM create_admin(deleted_user_id, admin_user_id);
        RAISE EXCEPTION 'Admin created with deleted user';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'User with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;


    BEGIN
        PERFORM create_admin(new_user_id, gen_random_uuid());
        RAISE EXCEPTION 'Admin does not exist';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'Admin with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'newUser2@example.com', 'Test User2')
    RETURNING users.user_id INTO new_user_id;

    UPDATE admins
    SET 
        status = 'DEACTIVATED'
    WHERE 
        user_id = admin_user_id;

    BEGIN
        PERFORM create_admin(new_user_id, admin_user_id);
        RAISE EXCEPTION 'Admin is deactivated';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'Admin with ID % is not active' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    UPDATE users
    SET 
        is_deleted = TRUE
    WHERE 
        user_id = admin_user_id;

    BEGIN
        PERFORM create_admin(new_user_id, admin_user_id);
        RAISE EXCEPTION 'Admin is deleted';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'Admin with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    DELETE FROM admin_roles;
    DELETE FROM admins;
    DELETE FROM users;

    RAISE NOTICE 'Test create admin tests passed';
END;
$$ LANGUAGE plpgsql;