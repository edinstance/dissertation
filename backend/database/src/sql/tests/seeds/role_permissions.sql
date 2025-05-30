-- This test validates the role_permissions seeds.
CREATE OR REPLACE PROCEDURE test.test_seed_role_permissions()
AS
$$
DECLARE
    role_permissions_returned INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO role_permissions_returned
    FROM role_permissions;

    -- Check if 1 role_permission was returned
    IF role_permissions_returned != 1 THEN
        RAISE EXCEPTION 'Role permissions not returned';
    END IF;


    RAISE NOTICE 'Role permissions seeds test passed';

END;
$$ LANGUAGE plpgsql;