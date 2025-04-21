-- This function inserts a new row into the user billing table if the user id does not exist,
-- otherwise it updates the existing row
CREATE OR REPLACE FUNCTION insert_or_update_user_billing (
    _user_id UUID,
    _account_id VARCHAR,
    _customer_id VARCHAR
) RETURNS TABLE (
    user_id UUID,
    account_id VARCHAR,
    customer_id VARCHAR
) AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE users.user_id = _user_id) THEN
        RAISE EXCEPTION 'User ID % does not exist in the users table', _user_id;
    END IF;

    -- If _user_id is provided, update the existing row
    IF _user_id IS NOT NULL THEN
        RETURN QUERY
        UPDATE user_billing SET
            account_id = _account_id,
            customer_id = _customer_id
        WHERE user_id = _user_id
        RETURNING user_billing.user_id, user_billing.account_id, user_billing.customer_id;
    ELSE
        -- Insert a new user billing row if _user_id is NULL
        RETURN QUERY
        INSERT INTO user_billing (
           user_id, account_id, customer_id
        ) VALUES (
            _user_id, _account_id, _customer_id
        )
        RETURNING user_billing.user_id, user_billing.account_id, user_billing.customer_id;
    END IF;
END;
$$ LANGUAGE plpgsql;