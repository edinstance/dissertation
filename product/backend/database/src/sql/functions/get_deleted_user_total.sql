-- This function gets the total amount of deleted users.
CREATE OR REPLACE FUNCTION get_deleted_user_total()
RETURNS TABLE (
    total_users BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT COUNT(*) AS total_users
    FROM users
    WHERE is_deleted = TRUE;
END;
$$ LANGUAGE plpgsql;