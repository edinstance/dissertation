-- This procedure deletes a users personal details and sets the is_deleted column to true
CREATE OR REPLACE PROCEDURE delete_user (
    _user_id UUID
) AS $$
BEGIN 
    -- Check if the user_id exists in the users table
    IF NOT EXISTS (SELECT 1 FROM users WHERE users.user_id = _user_id) THEN
        RAISE EXCEPTION 'User ID % does not exist in the users table', _user_id;
    END IF;

    -- Check if the user has already been deleted
    IF EXISTS (SELECT 1 FROM users WHERE users.user_id = _user_id AND users.is_deleted = TRUE) THEN
        RAISE EXCEPTION 'User ID % has already been deleted', _user_id;
    END IF;

    -- Set the is_deleted column to true
    UPDATE users SET is_deleted = TRUE WHERE users.user_id = _user_id;

    -- Delete the user's personal details
    DELETE FROM user_details WHERE user_details.user_id = _user_id;
END;
$$ LANGUAGE plpgsql;