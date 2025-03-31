CREATE OR REPLACE PROCEDURE test.test_check_user_and_admin_status()
AS $$
DECLARE
    admin_user_id   UUID;
    new_user_id     UUID;
    deleted_user_id UUID;
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
    VALUES (gen_random_uuid(), 'newUser@example.com', 'Test User')
    RETURNING users.user_id INTO new_user_id;

    BEGIN
        PERFORM check_user_and_admin_status(gen_random_uuid(), admin_user_id);
        RAISE EXCEPTION 'Function accepted non-existent user ID';
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
        PERFORM check_user_and_admin_status(deleted_user_id, admin_user_id);
        RAISE EXCEPTION 'Function accepted deleted user';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'User with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    BEGIN
        PERFORM check_user_and_admin_status(new_user_id, gen_random_uuid());
        RAISE EXCEPTION 'Function accepted non-existent admin';
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
        PERFORM check_user_and_admin_status(new_user_id, admin_user_id);
        RAISE EXCEPTION 'Function accepted deactivated admin';
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
        PERFORM check_user_and_admin_status(new_user_id, admin_user_id);
        RAISE EXCEPTION 'Function accepted deleted admin';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'Admin with ID % does not exist or is deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    DELETE FROM admins;
    DELETE FROM users;

    RAISE NOTICE 'All validation tests passed successfully';
END;
$$ LANGUAGE plpgsql;