-- This function gets the price of an item.
CREATE OR REPLACE FUNCTION get_item_price(
    _item_id UUID
)
RETURNS TABLE (
    item_price NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT i.price
    FROM items i
    WHERE i.item_id = _item_id;
END;
$$ LANGUAGE plpgsql;