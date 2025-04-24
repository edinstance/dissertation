CREATE OR REPLACE FUNCTION get_items_by_user(
    _user_id UUID,
    _is_active BOOLEAN DEFAULT TRUE,
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
    seller_id UUID,
    final_price DECIMAL
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        i.item_id, 
        i.name, 
        i.description,
        i.is_active,
        i.ending_time,
        i.price,
        i.stock,
        i.category,
        i.images,
        i.seller_id,
        i.final_price
    FROM items i
    WHERE i.seller_id = _user_id AND i.is_active = _is_active
    ORDER BY i.last_updated_at DESC
    LIMIT _page_size OFFSET _page * _page_size;
END;
$$ LANGUAGE plpgsql;
