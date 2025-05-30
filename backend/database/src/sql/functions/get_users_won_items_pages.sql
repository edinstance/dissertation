-- This function gets the total amount of pages for the users winning bids.
CREATE OR REPLACE FUNCTION get_users_won_items_pages(
    _buyer_id UUID,
    page_size INT DEFAULT 10
)
RETURNS TABLE (
    total_pages INT
) AS $$
DECLARE
    total_items INT;
BEGIN

    SELECT COUNT(*) INTO total_items
    FROM winning_bids wb
    JOIN items i ON wb.item_id = i.item_id
    WHERE wb.buyer_id = _buyer_id;

    RETURN QUERY SELECT CEIL(total_items::DECIMAL / page_size)::INT;
END;
$$ LANGUAGE plpgsql;
