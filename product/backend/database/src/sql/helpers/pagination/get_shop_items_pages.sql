-- This function gets the total pages for the shop page items.
CREATE OR REPLACE FUNCTION get_shop_items_pages(
    _page_size INT DEFAULT 10
)
RETURNS TABLE (
    total_pages INT
) AS $$ 
DECLARE
    total_items INT;
BEGIN

    SELECT COUNT(*) INTO total_items
    FROM items i
    WHERE is_active = true;

    RETURN QUERY SELECT GREATEST(1, CEIL(total_items::DECIMAL / _page_size)::INT);
END;
$$ LANGUAGE plpgsql;
