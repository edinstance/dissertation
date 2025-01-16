-- This tests the email constraints
CREATE OR REPLACE PROCEDURE test.test_email_constraints()
AS
$$
DECLARE
    current_user_id          UUID;
BEGIN

    -- Create a user
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User')
    RETURNING * INTO current_user_id;

    -- Create a user with the same email
    BEGIN
        INSERT INTO users (user_id, email, name)
        VALUES (gen_random_uuid(), 'test@test.com', 'User');
    EXCEPTION
        WHEN unique_violation THEN
            RAISE NOTICE 'Error is thrown when creating a user with the same email';
    END;
    
    -- Set the user as deleted
    UPDATE users SET is_deleted = TRUE WHERE user_id = current_user_id;

    -- Create a user with the same email
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User');

    RAISE NOTICE 'Email constraint works correctly';
END;
$$ LANGUAGE plpgsql;


-- This tests the email constraints
CREATE OR REPLACE PROCEDURE test.test_email_constraints()
AS
$$
DECLARE
    current_user_id UUID;
BEGIN

    -- Create a user
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User')
    RETURNING * INTO current_user_id;
    
    -- Set the user as deleted
    UPDATE users SET is_deleted = TRUE WHERE user_id = current_user_id;


    -- Create a user with the same email
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User');

    RAISE NOTICE 'Email constraint works correctly';

END;
$$ LANGUAGE plpgsql;