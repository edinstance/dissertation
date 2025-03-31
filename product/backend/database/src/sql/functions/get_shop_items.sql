CREATE OR REPLACE FUNCTION get_shop_items(
    _order_by VARCHAR DEFAULT 'ending_time',
    _order_direction sort_order_direction DEFAULT 'ASC',
    _page INT DEFAULT 0,
    _page_size INT DEFAULT 10
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
) AS $$
BEGIN
    -- Validate that _order_by is a valid column in the items table
    IF NOT EXISTS (
        SELECT 1
        FROM pg_attribute
        WHERE attrelid = 'items'::regclass
          AND attname = _order_by
          AND attnum > 0
          AND NOT attisdropped
    ) THEN
        RAISE EXCEPTION 'Invalid column name for ordering: %', _order_by;
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
        ORDER BY %I %s
        LIMIT $1 OFFSET $2',
        _order_by,
        _order_direction
    )
    USING _page_size, _page * _page_size;
END;
$$ LANGUAGE plpgsql;
