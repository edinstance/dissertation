CREATE OR REPLACE PROCEDURE test.test_get_deleted_users()
AS $$
DECLARE
  deleted_users_result BIGINT;
BEGIN

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'user1@example.com', 'Active User 1');

    IF deleted_users_result != 0 THEN
        RAISE EXCEPTION 'get_deleted_users() failed: Expected 0, got %', deleted_users_result;
    END IF;

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'user2@example.com', 'Active User 2');

    IF deleted_users_result != 0 THEN
        RAISE EXCEPTION 'get_deleted_users() failed: Expected 0, got %', deleted_users_result;
    END IF;
    
    INSERT INTO users (user_id, email, name, is_deleted)
    VALUES (gen_random_uuid(), 'user3@example.com', 'Deleted User', TRUE);

    SELECT total_users INTO deleted_users_result FROM get_deleted_user_total();
    
    IF deleted_users_result != 1 THEN
        RAISE EXCEPTION 'get_deleted_users() failed: Expected 1, got %', deleted_users_result;
    END IF;

    DELETE FROM users;

    RAISE NOTICE 'get deleted users tests passed';
END;
$$ LANGUAGE plpgsql;
