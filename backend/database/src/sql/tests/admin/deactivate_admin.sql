CREATE OR REPLACE PROCEDURE test.test_deactivate_admin()
AS $$
DECLARE
    admin_user_id               UUID;
    second_admin_user_id        UUID;
    deactivated_admin_count     INTEGER;
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

    -- Deactivate the second admin
    CALL deactivate_admin(second_admin_user_id, admin_user_id);

    SELECT COUNT(*)
    INTO deactivated_admin_count
    FROM admin_roles
    WHERE admin_id = second_admin_user_id;

    IF deactivated_admin_count != 0 THEN
        RAISE EXCEPTION 'Admin roles not deleted';
    END IF;

    SELECT COUNT(*)
    INTO deactivated_admin_count
    FROM admin_permissions
    WHERE admin_id = second_admin_user_id;

    IF deactivated_admin_count != 0 THEN
        RAISE EXCEPTION 'Admin permissions not deleted';
    END IF;

    SELECT COUNT(*)
    INTO deactivated_admin_count
    FROM admins
    WHERE
        user_id = second_admin_user_id AND
        status = 'DEACTIVATED' AND
        is_super_admin = FALSE AND
        is_deleted = FALSE;

    IF deactivated_admin_count != 1 THEN
        RAISE EXCEPTION 'Admin not deativated correctly %1', deactivated_admin_count;
    END IF;

    SELECT COUNT(*)
    INTO deactivated_admin_count
    FROM users
    WHERE
        user_id = second_admin_user_id AND
        is_deleted = TRUE;

    IF deactivated_admin_count != 0 THEN
        RAISE EXCEPTION 'Admin user was deleted when it should not have been';
    END IF;


    DELETE FROM admins;
    DELETE FROM users;

    RAISE NOTICE 'Deactivate admin tests all passed.';
END;
$$ LANGUAGE plpgsql;