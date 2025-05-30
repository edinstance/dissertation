-- This tests the insert_or_update_item function
CREATE OR REPLACE PROCEDURE test.test_insert_or_update_item()
AS
$$
DECLARE
    seller_id      UUID;
    result_item_id UUID;
    item_data      RECORD;
BEGIN
    -- Insert a seller into the users table and get their user_id
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'Test Seller')
    RETURNING user_id INTO seller_id;

    -- Call insert_or_update_item, using explicit schema and casting
    SELECT item_id
    INTO result_item_id
    FROM insert_or_update_item(
            NULL::UUID,
            'item'::VARCHAR,
            'description'::TEXT,
            TRUE,
            NOW()::TIMESTAMP,
            99.99::NUMERIC,
            10::INT,
            'category'::VARCHAR,
            '[
              "image1",
              "image2"
            ]'::JSONB,
            seller_id::UUID
         );

    -- Validate the data in the items table
    SELECT *
    INTO item_data
    FROM items
    WHERE item_id = result_item_id;

    -- Assert specific values for the inserted row
    IF item_data.name != 'item' OR
       item_data.description != 'description' OR
       item_data.is_active != TRUE OR
       item_data.price != 99.99 OR
       item_data.stock != 10 OR
       item_data.category != 'category' OR
       item_data.images != '[
         "image1",
         "image2"
       ]'::JSONB OR
       item_data.seller_id != seller_id
    THEN
        RAISE EXCEPTION 'Item data does not match expected values: %', item_data;
    ELSE
        RAISE NOTICE 'Item data matches expected values.';
    END IF;

    -- Update the item
    PERFORM insert_or_update_item(
            result_item_id::UUID,
            'new item'::VARCHAR,
            'new description'::TEXT,
            FALSE,
            NOW()::TIMESTAMP,
            9.9::NUMERIC,
            5::INT,
            'new category'::VARCHAR,
            '[
              "image3",
              "image4"
            ]'::JSONB,
            seller_id::UUID
            );


    -- Retrive the new item data
    SELECT *
    INTO item_data
    FROM items
    WHERE item_id = result_item_id;

    -- Assert that the values have been updated
    IF item_data.name != 'new item' OR
       item_data.description != 'new description' OR
       item_data.is_active != FALSE OR
       item_data.price != 9.9 OR
       item_data.stock != 5 OR
       item_data.category != 'new category' OR
       item_data.images != '[
         "image3",
         "image4"
       ]'::JSONB OR
       item_data.seller_id != seller_id
    THEN
        RAISE EXCEPTION 'Item data does not match expected values: %', item_data;
    ELSE
        RAISE NOTICE 'Item update is successful.';
    END IF;

    -- Remove the test data
    DELETE FROM items;
    DELETE FROM users;

    RAISE NOTICE 'Function works correctly';
END;
$$ LANGUAGE plpgsql;