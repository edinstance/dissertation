-- This function gets the total amoutn of pages for the search results.
CREATE OR REPLACE FUNCTION get_item_search_pages(
    search_text VARCHAR,
    page_size INT DEFAULT 10
)
RETURNS TABLE (
    total_pages INT
) AS $$ 
DECLARE
    total_items INT;
BEGIN

    SELECT COUNT(*) INTO total_items
    FROM items i
    WHERE similarity(i.name, search_text) > 0.3;

    RETURN QUERY SELECT CEIL(total_items::DECIMAL / page_size)::INT;
END;
$$ LANGUAGE plpgsql;