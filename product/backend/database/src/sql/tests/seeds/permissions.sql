-- This test validates the permission seeds.
CREATE OR REPLACE PROCEDURE test.test_seed_permissions()
AS
$$
DECLARE
    permissions_returned INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO permissions_returned
    FROM permissions;

    -- Check if 1 permission was returned
    IF permissions_returned != 1 THEN
        RAISE EXCEPTION 'Permission not returned';
    END IF;


    RAISE NOTICE 'Permission seeds test passed';

END;
$$ LANGUAGE plpgsql;