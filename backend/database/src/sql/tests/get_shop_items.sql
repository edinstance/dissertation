-- Create the test procedure
CREATE OR REPLACE PROCEDURE test.test_get_items_by_user()
AS $$
DECLARE
    seller_id      UUID;
    items_returned INT;
    item           RECORD;
BEGIN
    INSERT INTO users (user_id, email, name)
    VALUES (gen_random_uuid(), 'test@test.com', 'Test Seller')
    RETURNING user_id INTO seller_id;

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

    SELECT COUNT(*)
    INTO items_returned
    FROM get_shop_items();

    -- Check if all 10 items were returned
    IF items_returned != 10 THEN
        RAISE EXCEPTION 'Expected 10 items, got %', items_returned;
    END IF;

    SELECT COUNT(*)
    INTO items_returned
    FROM get_shop_items('ending_time', 'ASC', 0, 10);

    -- Check if parameters are working correctly
    IF items_returned != 10 THEN
        RAISE EXCEPTION 'Test 2 failed: Expected 10 items, got %', items_returned;
    END IF;

    -- Check if the pagination is working correctly
    SELECT COUNT(*)
    INTO items_returned
    FROM get_shop_items('ending_time', 'ASC', 0, 6);

    -- Check if exactly 6 items were returned
    IF items_returned != 6 THEN
        RAISE EXCEPTION 'Test 3 failed: Expected 6 items, got %', items_returned;
    END IF;

    -- Check if the second page is working correctly
    SELECT COUNT(*)
    INTO items_returned
    FROM get_shop_items('ending_time', 'ASC', 1, 6);

    -- Check if exactly 4 items were returned
    IF items_returned != 4 THEN
        RAISE EXCEPTION 'Test 4 failed: Expected 4 items, got %', items_returned;
    END IF;


    BEGIN
        PERFORM get_shop_items('test_exeption', 'ASC', 1, 6);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE NOTICE 'Test 5 exeption thrown as expected';
    END;

    BEGIN
        PERFORM get_shop_items('ending_time', 'test_exception', 1, 6);
    EXCEPTION
        WHEN invalid_text_representation THEN
            RAISE NOTICE 'Test 6 exeption thrown as expected';
    END;

    -- Insert items with lower and higher prices
    PERFORM insert_or_update_item(
        NULL::UUID,
        'item'::VARCHAR,
        'description'::TEXT,
        TRUE,
        NOW()::TIMESTAMP,
        10::NUMERIC,
        10::INT,
        'category'::VARCHAR,
        '[
            "image1",
            "image2"
        ]'::JSONB,
        seller_id::UUID
        );

    PERFORM insert_or_update_item(
        NULL::UUID,
        'item'::VARCHAR,
        'description'::TEXT,
        TRUE,
        NOW()::TIMESTAMP,
        100::NUMERIC,
        10::INT,
        'category'::VARCHAR,
        '[
            "image1",
            "image2"
        ]'::JSONB,
        seller_id::UUID
        );

    -- Check if the ordering is working correctly
    SELECT *
    INTO item
    FROM get_shop_items('price', 'ASC', 0, 1);

    IF item.price != 10 THEN
        RAISE EXCEPTION 'Test 7 failed: Expected price 10, got %', item.price;
    END IF;

    SELECT *
    INTO item
    FROM get_shop_items('price', 'DESC', 0, 1);

    IF item.price != 100 THEN
        RAISE EXCEPTION 'Test 8 failed: Expected price 100, got %', item.price;
    END IF;

    -- Cleanup test data
    DELETE FROM items;
    DELETE FROM users;

    RAISE NOTICE 'All tests passed successfully';
END;
$$ LANGUAGE plpgsql;