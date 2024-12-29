-- This tests the view for a user with details
CREATE OR REPLACE PROCEDURE test.test_search_for_items()
AS
$$
DECLARE
    seller_id      UUID;
    items_returned INT;
BEGIN

    -- Insert a seller into the users table and get their user_id
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'Test Seller')
    RETURNING user_id INTO seller_id;

    -- Add an item
    PERFORM insert_or_update_item(
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

    -- Get the count of items returned from the search function
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('item');

    -- Check if exactly 1 item was returned
    IF items_returned != 1 THEN
        RAISE EXCEPTION 'Item not returned in search';
    END IF;

    -- Add another item with a similar name
    PERFORM insert_or_update_item(
            NULL::UUID,
            'item2'::VARCHAR,
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

    -- Repeat the search
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('item');

    -- Check if multiple items are now returned
    IF items_returned != 2 THEN
        RAISE EXCEPTION 'Item not returned in search';
    END IF;

    -- Search for a very different item
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('nothing');

    -- Check if no items are returned
    IF items_returned != 0 THEN
        RAISE EXCEPTION 'Item not returned in search';
    END IF;

    -- Cleanup test data
    DELETE FROM items;
    DELETE FROM users;

    RAISE NOTICE 'Function works correctly';
END;
$$ LANGUAGE plpgsql;
