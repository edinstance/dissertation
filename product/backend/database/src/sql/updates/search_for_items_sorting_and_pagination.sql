-- This function searches for items in the items table based on the similarity of the item name to the search text.
-- It now includes pagination so not all the values are returned at once and
-- sorting so that a user can choose the order they recieve the data in.

DROP FUNCTION IF EXISTS search_for_items(character varying, integer, integer);

CREATE OR REPLACE FUNCTION search_for_items(
    search_text VARCHAR,
    order_by VARCHAR DEFAULT 'ending_time',
    order_direction sort_order_direction DEFAULT 'ASC',
    page INT DEFAULT 0,
    page_size INT DEFAULT 10
)
RETURNS TABLE (
    item_id UUID,
    name VARCHAR,
    description TEXT,
    is_active BOOLEAN,
    ending_time TIMESTAMP,
    price DECIMAL,
    stock INT,
    category VARCHAR,
    images JSONB,
    seller_id UUID
) AS $$ BEGIN

 IF NOT EXISTS (
        SELECT 1
        FROM pg_attribute
        WHERE attrelid = 'items'::regclass
          AND attname = order_by
          AND attnum > 0
          AND NOT attisdropped
    ) THEN
        RAISE EXCEPTION 'Invalid column name for ordering: %', order_by;
    END IF;

    RETURN QUERY EXECUTE format(
        'SELECT
            i.item_id, 
            i.name, 
            i.description,
            i.is_active,
            i.ending_time,
            i.price,
            i.stock,
            i.category,
            i.images,
            i.seller_id
        FROM items i
        WHERE similarity(i.name, $1) > 0.3
        AND is_active = true
        ORDER BY %I %s
        LIMIT $2 OFFSET $3', 
        order_by,
        order_direction
    )
     USING search_text, page_size, page * page_size;
END;
$$ LANGUAGE plpgsql;