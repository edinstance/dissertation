CREATE OR REPLACE PROCEDURE test.test_delete_admin()
AS $$
DECLARE
    admin_user_id           UUID;
    second_admin_user_id    UUID;
    delete_admin_counts     INTEGER;
BEGIN
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
    VALUES (gen_random_uuid(), 'admin2@example.com', 'Test Admin 2')
    RETURNING users.user_id INTO second_admin_user_id;

    INSERT INTO admins (
        user_id,
        is_super_admin,
        status,
        created_by,
        last_updated_by,
        is_deleted
    ) VALUES (
        second_admin_user_id,
        TRUE,
        'ACTIVE',
        admin_user_id,
        admin_user_id,
        FALSE
    );

    -- Delete the second admin
    CALL delete_admin(second_admin_user_id, admin_user_id);

    SELECT COUNT(*)
    INTO delete_admin_counts
    FROM admin_roles
    WHERE admin_id = second_admin_user_id;

    IF delete_admin_counts != 0 THEN
        RAISE EXCEPTION 'Admin roles not deleted';
    END IF;

    SELECT COUNT(*)
    INTO delete_admin_counts
    FROM admin_permissions
    WHERE admin_id = second_admin_user_id;

    IF delete_admin_counts != 0 THEN
        RAISE EXCEPTION 'Admin not deleted';
    END IF;

    SELECT COUNT(*)
    INTO delete_admin_counts
    FROM admins
    WHERE
        user_id = second_admin_user_id AND
        status = 'DEACTIVATED' AND
        is_super_admin = FALSE AND
        is_deleted = TRUE;

    IF delete_admin_counts != 1 THEN
        RAISE EXCEPTION 'Admin not deleted correctly %1', delete_admin_counts;
    END IF;

    SELECT COUNT(*)
    INTO delete_admin_counts
    FROM users
    WHERE
        user_id = second_admin_user_id AND
        is_deleted = TRUE;

    IF delete_admin_counts != 1 THEN
        RAISE EXCEPTION 'Admin user not deleted correctly, %1 ahh', delete_admin_counts;
    END IF;


    DELETE FROM admins;
    DELETE FROM users;

    RAISE NOTICE 'Delete admin tests all passed.';
END;
$$ LANGUAGE plpgsql;