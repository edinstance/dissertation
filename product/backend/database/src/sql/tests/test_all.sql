-- This procedure runs all the tests
CREATE OR REPLACE PROCEDURE test.test_all()
AS
$$
DECLARE
    procedure_name TEXT;
BEGIN
    -- It looks through all the procedures in the test schema
    -- They are retrieved from pg_proc and pg_namespace
    -- The test_all proname is excluded so the tests do not keep runing
    -- The procedure name is formatted with the query so that it can easily be ran
    FOR procedure_name IN
        SELECT format('CALL %I.%I()', ns.nspname, p.proname)
        FROM pg_proc p
                 INNER JOIN pg_namespace ns ON p.pronamespace = ns.oid
        WHERE ns.nspname = 'test'
          AND p.proname <> 'test_all'

        LOOP
            BEGIN
                EXECUTE procedure_name; -- Execute the query
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE EXCEPTION 'Error executing %', procedure_name;
                -- Raise an exception if any of the procedures raise exceptions
            END;
        END LOOP;

    RAISE NOTICE 'Test passed: All procedures executed.';
END;
$$ LANGUAGE plpgsql;