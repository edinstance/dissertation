-- This test confirms pg_trgm is added
CREATE OR REPLACE PROCEDURE test.test_pg_trgm()
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_extension WHERE extname = 'pg_trgm') THEN
        RAISE EXCEPTION 'pg_trgm extension is not installed';
    ELSE
        RAISE NOTICE 'pg_trgm extension is installed';
    END IF;
END;
$$ LANGUAGE plpgsql;