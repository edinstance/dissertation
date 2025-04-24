-- This function gets the items with finished auctions that haven't been processed
CREATE OR REPLACE FUNCTION get_items_from_finished_auctions()
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
    UPDATE items i
    SET is_active = false
    WHERE i.ending_time < now()
      AND i.is_active = true
    RETURNING
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
        i.final_price;
END;
$$ LANGUAGE plpgsql;
