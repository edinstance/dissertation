CREATE OR REPLACE PROCEDURE test.test_get_total_users()
AS $$
DECLARE
  total_users_result BIGINT;
BEGIN

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'user1@example.com', 'Active User 1');

    SELECT total_users INTO total_users_result FROM get_total_users();

    IF total_users_result != 1 THEN
        RAISE EXCEPTION 'get_total_users() failed: Expected 1, got %', total_users_result;
    END IF;

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'user2@example.com', 'Active User 2');

    SELECT total_users INTO total_users_result FROM get_total_users();

    IF total_users_result != 2 THEN
        RAISE EXCEPTION 'get_total_users() failed: Expected 2, got %', total_users_result;
    END IF;
    
    INSERT INTO users (user_id, email, name, is_deleted)
    VALUES (gen_random_uuid(), 'user3@example.com', 'Deleted User', TRUE);

    SELECT total_users INTO total_users_result FROM get_total_users();

    IF total_users_result != 2 THEN
        RAISE EXCEPTION 'get_total_users() failed: Expected 2, got %', total_users_result;
    END IF;

    DELETE FROM users;

    RAISE NOTICE 'get total users tests passed';
END;
$$ LANGUAGE plpgsql;
