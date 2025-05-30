-- This tests getting items from a user.
CREATE OR REPLACE PROCEDURE test.test_get_items_by_user()
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

    -- Add 10 items
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
    FROM get_items_by_user(seller_id);

    -- Check if exactly 10 items were returned
    IF items_returned != 10 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;
    

    -- Get the count of items returned from the search function with a random UUID
    SELECT COUNT(*)
    INTO items_returned
    FROM get_items_by_user(gen_random_uuid());

    -- Check if exactly 10 items were returned
    IF items_returned != 0 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Get the count of items returned from the search function with a page size of 6
    SELECT COUNT(*)
    INTO items_returned
    FROM get_items_by_user(seller_id, TRUE, 0, 6);

    -- Check if exactly 6 items were returned
    IF items_returned != 6 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Get the count of the second page with size 6
    SELECT COUNT(*)
    INTO items_returned
    FROM get_items_by_user(seller_id, TRUE, 1, 6);

    -- Check if exactly 4 items were returned
    IF items_returned != 4 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- insert a non active item
    PERFORM insert_or_update_item(
        NULL::UUID,
        'item'::VARCHAR,
        'description'::TEXT,
        FALSE,
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

    -- Get the count of the second page with size 6
    SELECT COUNT(*)
    INTO items_returned
    FROM get_items_by_user(seller_id, FALSE);

    -- Check if exactly 1 item was returned
    IF items_returned != 1 THEN
        RAISE EXCEPTION 'Correct number of items was not returned in search';
    END IF;

    -- Cleanup test data
    DELETE FROM items;
    DELETE FROM users;

    RAISE NOTICE 'Function works correctly';
END;
$$ LANGUAGE plpgsql;
