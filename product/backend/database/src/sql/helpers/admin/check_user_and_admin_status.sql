CREATE OR REPLACE FUNCTION check_user_and_admin_status(
    _user_id UUID,
    _admin_id UUID
) RETURNS VOID AS $$
BEGIN

    IF NOT EXISTS(
        SELECT 1 FROM users
        WHERE users.user_id = _admin_id
        AND users.is_deleted = FALSE
    ) THEN
        RAISE EXCEPTION 'Admin with ID % does not exist or is deleted', _admin_id;
    END IF;

    IF NOT EXISTS(
        SELECT 1 FROM admins
        WHERE admins.user_id = _admin_id
        AND admins.is_deleted = FALSE
        AND admins.status != 'DEACTIVATED'
    ) THEN
        RAISE EXCEPTION 'Admin with ID % is not active', _admin_id;
    END IF;

    IF NOT EXISTS(
        SELECT 1 FROM users
        WHERE users.user_id = _user_id AND users.is_deleted = FALSE
    ) THEN
        RAISE EXCEPTION 'User with ID % does not exist or is deleted', _user_id;
    END IF;

END;
$$ LANGUAGE plpgsql;