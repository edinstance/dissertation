-- This tests the insert_or_update_user_details procedure
CREATE OR REPLACE PROCEDURE test.test_insert_or_update_user_details()
AS
$$
DECLARE
    current_user_id      UUID;
    current_user_details RECORD;
BEGIN

    -- Create a user
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'User')
    RETURNING * INTO current_user_id;

    -- Created details for that user
    CALL insert_or_update_user_details(current_user_id, '07777 777777',
                                       'house', 'street',
                                       'city', 'county',
                                       'postcode');

    -- Get the details
    SELECT * INTO current_user_details FROM user_details WHERE user_id = current_user_id;

    -- Check the details are correct
    IF current_user_details.contact_number != '07777 777777' OR
       current_user_details.house_name != 'house' OR
       current_user_details.address_street != 'street' OR
       current_user_details.address_city != 'city' OR
       current_user_details.address_county != 'county' OR
       current_user_details.address_post_code != 'postcode'
    THEN
        RAISE EXCEPTION 'User details are not correct';
    ELSE
        RAISE NOTICE 'User details created successfully';
    END IF;

    -- Update the details
    CALL insert_or_update_user_details(current_user_id, 'new number',
                                       'new house', 'new street',
                                       'new city', 'new county',
                                       'new postcode');

    -- Get the updated details
    SELECT * INTO current_user_details FROM user_details WHERE user_id = current_user_id;

    -- Check the details have been updated
    IF current_user_details.contact_number != 'new number' OR
       current_user_details.house_name != 'new house' OR
       current_user_details.address_street != 'new street' OR
       current_user_details.address_city != 'new city' OR
       current_user_details.address_county != 'new county' OR
       current_user_details.address_post_code != 'new postcode'
    THEN
        RAISE EXCEPTION 'User details were not updated correctly';
    ELSE
        RAISE NOTICE 'User details updated successfully';
    END IF;

    -- Remove the test data
    DELETE FROM user_details;
    DELETE FROM users;


    RAISE NOTICE 'Procedure works correctly';
END;
$$ LANGUAGE plpgsql;
