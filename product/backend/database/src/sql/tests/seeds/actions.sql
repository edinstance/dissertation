-- This test validates the actions seeds.
CREATE OR REPLACE PROCEDURE test.test_seed_actions()
AS
$$
DECLARE
    actions_returned INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO actions_returned
    FROM actions;

    -- Check if 2 actions were returned
    IF actions_returned != 2 THEN
        RAISE EXCEPTION 'Actions not returned';
    END IF;


    RAISE NOTICE 'Action seeds test passed';

END;
$$ LANGUAGE plpgsql;