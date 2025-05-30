-- This tests the view for a user with details
CREATE OR REPLACE PROCEDURE test.test_user_with_details_view()
AS
$$
DECLARE
    current_user_id          UUID;
    user_with_details_result RECORD;
BEGIN

    -- Create a user
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User')
    RETURNING * INTO current_user_id;

    -- Create details for the user
    CALL insert_or_update_user_details(current_user_id, '07777 777777',
                                       'house', 'street',
                                       'city', 'county',
                                       'postcode');

    -- Get the value from the view
    SELECT * INTO user_with_details_result FROM user_with_details WHERE user_id = current_user_id;

    -- Check the details are correct
    IF user_with_details_result.user_id != current_user_id OR
       user_with_details_result.email != 'test@test.com' OR
       user_with_details_result.name != 'User' OR
       user_with_details_result.contact_number != '07777 777777' OR
       user_with_details_result.address_street != 'street' OR
       user_with_details_result.address_city != 'city' OR
       user_with_details_result.address_county != 'county' OR
       user_with_details_result.address_post_code != 'postcode'
    THEN
        RAISE EXCEPTION 'The result is incorrect';
    ELSE
        RAISE NOTICE 'The result is correct';
    END IF;


    -- Cleanup test data
    DELETE FROM user_details;
    DELETE FROM users;

    RAISE NOTICE 'View works correctly';

END;
$$ LANGUAGE plpgsql;