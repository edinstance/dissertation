-- This procedure makes a user a super admin.
CREATE OR REPLACE PROCEDURE make_admin_super_admin (
    _user_id UUID,
    _admin_id UUID
) AS $$
BEGIN
    PERFORM check_user_and_admin_status(_user_id, _admin_id);

    UPDATE admins
    SET
        is_super_admin = TRUE,
        last_updated_by = _admin_id
    WHERE
        user_id = _user_id;

END;
$$ LANGUAGE plpgsql;