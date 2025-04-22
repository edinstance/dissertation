-- This function inserts a new row into the user billing table if the user id does not exist,
-- otherwise it updates the existing row
CREATE OR REPLACE PROCEDURE insert_or_update_user_billing (
    _user_id UUID,
    _account_id VARCHAR,
    _customer_id VARCHAR
) AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE users.user_id = _user_id) THEN
        RAISE EXCEPTION 'User ID % does not exist in the users table', _user_id;
    END IF;

    -- If the row exists update it
    IF EXISTS (SELECT 1 FROM user_billing WHERE user_id = _user_id) THEN
        UPDATE user_billing SET
            account_id = _account_id,
            customer_id = _customer_id
        WHERE user_id = _user_id;
    ELSE
        -- Insert a new user billing row if it does not exist.
        INSERT INTO user_billing (
           user_id, account_id, customer_id
        ) VALUES (
            _user_id, _account_id, _customer_id
        );
    END IF;
END;
$$ LANGUAGE plpgsql;