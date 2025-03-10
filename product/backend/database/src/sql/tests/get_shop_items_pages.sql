-- This tests getting the pages of users items.
CREATE OR REPLACE PROCEDURE test.test_get_shop_items_pages()
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

    -- Get the default page count
    SELECT total_pages
    INTO items_returned
    FROM get_shop_items_pages();

    -- Check if exactly 1 page was returned
    IF items_returned != 1 THEN
        RAISE EXCEPTION 'Expected 1 page for active items with default page size, got %', items_returned;
    END IF;

    -- Get the pages for items that aren't active
    SELECT total_pages
    INTO items_returned
    FROM get_shop_items_pages(10);

    -- Check if exactly 1 page was returned
    IF items_returned != 1 THEN
        RAISE EXCEPTION 'Expected 1 page for inactive items with default page size, got %', items_returned;
    END IF;

    -- Get the pages for a page size of 6
    SELECT total_pages
    INTO items_returned
    FROM get_shop_items_pages(6);

    -- Check if exactly 2 pages were returned
    IF items_returned != 2 THEN
        RAISE EXCEPTION 'Expected 2 pages for active items with page size 6, got %', items_returned;
    END IF;

    -- Cleanup test data
    DELETE FROM items;
    DELETE FROM users;

END;
$$ LANGUAGE plpgsql;