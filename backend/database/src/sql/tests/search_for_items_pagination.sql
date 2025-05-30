-- This tests the view for a user with details
CREATE OR REPLACE PROCEDURE test.test_search_for_items_pagination()
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
    FOR i IN 1..10 LOOP
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
    END LOOP;

    -- Get the count of items returned from the search function with the default pagination which is 10
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('item');

    -- Check if exactly 10 items were returned
    IF items_returned != 10 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Get the count of items returned from the search function with a page size of 6
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('item', 0, 6);

    -- Check if exactly 6 items were returned
    IF items_returned != 6 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Get the count of the second page with size 6
    SELECT COUNT(*)
    INTO items_returned
    FROM search_for_items('item', 1, 6);

    -- Check if exactly 4 items were returned
    IF items_returned != 4 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Cleanup test data
    DELETE FROM items;
    DELETE FROM users;

    RAISE NOTICE 'Function works correctly';
END;
$$ LANGUAGE plpgsql;
