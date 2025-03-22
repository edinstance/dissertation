-- This test validates the resource seeds.
CREATE OR REPLACE PROCEDURE test.test_seed_resources()
AS
$$
DECLARE
    resources_returned INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO resources_returned
    FROM resources;

    -- Check if 2 resources were returned
    IF resources_returned != 2 THEN
        RAISE EXCEPTION 'Resources not returned';
    END IF;


    RAISE NOTICE 'Resource seeds test passed';

END;
$$ LANGUAGE plpgsql;