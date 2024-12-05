-- This function searches for items in the items table based on the similarity of the item name to the search text.
CREATE OR REPLACE FUNCTION search_for_items(
    search_text VARCHAR
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
        i.seller_id
    FROM items i
    WHERE similarity(i.name, search_text) > 0.3
    ORDER BY similarity(i.name, search_text) DESC;
END;
$$ LANGUAGE plpgsql;