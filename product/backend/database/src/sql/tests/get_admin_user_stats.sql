CREATE OR REPLACE PROCEDURE test.test_admin_user_stats()
AS $$
DECLARE
  stats_results RECORD;
BEGIN

    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'user1@example.com', 'Active User 1');

    SELECT * INTO stats_results FROM get_admin_user_stats();

    IF stats_results.total_users != 1 THEN
        RAISE EXCEPTION 'get_admin_user_stats() failed: Expected 1, got %', stats_results.total_users;
    END IF;

    INSERT INTO users (user_id, email, name, created_at)
    VALUES (gen_random_uuid(), 'user2@example.com', 'Active User 2', '2020-01-01');

    SELECT * INTO stats_results FROM get_admin_user_stats();

    IF stats_results.total_users != 2 AND stats_results.new_users != 1 THEN
        RAISE EXCEPTION 'get_admin_user_stats() failed: Expected 2 users, got %1, Expected 1 new user, got %2', stats_results.total_users, stats_results.new_users;
    END IF;
    
    INSERT INTO users (user_id, email, name, is_deleted)
    VALUES (gen_random_uuid(), 'user3@example.com', 'Deleted User', TRUE);

    SELECT * INTO stats_results FROM get_admin_user_stats();

    IF stats_results.total_users != 2 AND stats_results.new_users != 1 AND stats_results.deleted_users != 1 THEN
        RAISE EXCEPTION 'get_admin_user_stats() failed: Expected 2 users, got %1, Expected 1 new user, got %2, Expected 1 deleted user, got %3',
         stats_results.total_users, stats_results.new_users, stats_results.deleted_users;
    END IF;


    DELETE FROM users;

    RAISE NOTICE 'get admin user stats tests passed';
END;
$$ LANGUAGE plpgsql;
