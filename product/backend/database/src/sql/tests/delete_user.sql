-- This tests the delete_user procedure
CREATE OR REPLACE PROCEDURE test.test_delete_user()
AS
$$
DECLARE
    test_user_id UUID;
BEGIN
    -- Create a test user
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'Test User')
    RETURNING user_id INTO test_user_id;

    -- Create user details for the test user
    INSERT INTO user_details (user_id, contact_number, house_name, address_street, address_city, address_county, address_post_code)
    VALUES (test_user_id, '07777777777', 'House', 'Street', 'City', 'County', 'PC1 1PC');

    -- Test deleting the user
    CALL delete_user(test_user_id);

    -- Verify user is marked as deleted
    IF NOT EXISTS (SELECT 1 FROM users WHERE user_id = test_user_id AND is_deleted = TRUE) THEN
        RAISE EXCEPTION 'User was not marked as deleted';
    END IF;

    -- Verify user details were deleted
    IF EXISTS (SELECT 1 FROM user_details WHERE user_id = test_user_id) THEN
        RAISE EXCEPTION 'User details were not deleted';
    END IF;

    -- Test deleting an already deleted user
    BEGIN
        CALL delete_user(test_user_id);
        RAISE EXCEPTION 'Should not be able to delete an already deleted user';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'User ID % has already been deleted' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    -- Test deleting a non-existent user
    BEGIN
        CALL delete_user(gen_random_uuid());
        RAISE EXCEPTION 'Should not be able to delete a non-existent user';
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLERRM NOT LIKE 'User ID % does not exist in the users table' THEN
                RAISE EXCEPTION 'Unexpected error message: %', SQLERRM;
            END IF;
    END;

    -- Clean up
    DELETE FROM users WHERE user_id = test_user_id;

    RAISE NOTICE 'Procedure works correctly';
END;
$$ LANGUAGE plpgsql;