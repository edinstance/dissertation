-- This test validates the roles seeds.
CREATE OR REPLACE PROCEDURE test.test_seed_roles()
AS
$$
DECLARE
    roles_returned INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO roles_returned
    FROM roles;

    -- Check if 1 role was returned
    IF roles_returned != 1 THEN
        RAISE EXCEPTION 'Roles not returned';
    END IF;


    RAISE NOTICE 'Role seeds test passed';

END;
$$ LANGUAGE plpgsql;