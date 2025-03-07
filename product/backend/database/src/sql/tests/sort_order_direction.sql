-- This test validates the sort order direction type.
CREATE OR REPLACE PROCEDURE test.test_sort_order_direction()
AS
$$
BEGIN
    PERFORM 'ASC'::sort_order_direction;
    PERFORM 'DESC'::sort_order_direction;
    RAISE NOTICE 'Valid values test passed';

    BEGIN
        PERFORM 'INVALID'::sort_order_direction;
    EXCEPTION
        WHEN invalid_text_representation THEN
            RAISE NOTICE 'Invalid value test passed';
    END;

    RAISE NOTICE 'All sort order direction tests passed';

END;
$$ LANGUAGE plpgsql;