CREATE OR REPLACE FUNCTION get_users_won_items(
    _buyer_id UUID,
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
        wb.final_price
    FROM winning_bids wb
    JOIN items i ON wb.item_id = i.item_id
    WHERE wb.buyer_id = _buyer_id
    ORDER BY i.last_updated_at DESC
    LIMIT _page_size OFFSET _page * _page_size;
END;
$$ LANGUAGE plpgsql;