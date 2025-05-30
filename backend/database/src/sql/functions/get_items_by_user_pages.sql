-- This function gets the total amoutn of pages for the search results.
CREATE OR REPLACE FUNCTION get_items_by_user_pages(
    _user_id UUID,
    _is_active BOOLEAN DEFAULT TRUE,
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
    WHERE seller_id = _user_id AND is_active = _is_active;

    RETURN QUERY SELECT GREATEST(1, CEIL(total_items::DECIMAL / _page_size)::INT);
END;
$$ LANGUAGE plpgsql;
