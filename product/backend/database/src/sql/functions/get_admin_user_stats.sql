-- This function gets the total amount of new users.
CREATE OR REPLACE FUNCTION get_admin_user_stats()
RETURNS TABLE (
    total_users BIGINT,
    new_users BIGINT,
    deleted_users BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        (SELECT t.total_users FROM get_total_users() t) AS total_users,
        (SELECT n.total_users FROM get_new_user_total() n) AS new_users,
        (SELECT d.total_users FROM get_deleted_user_total() d) AS deleted_users;
END;
$$ LANGUAGE plpgsql;