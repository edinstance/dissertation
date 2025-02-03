-- This function inserts a new row into the user_details table if the user_id does not exist, 
-- otherwise it updates the existing row 
CREATE OR REPLACE FUNCTION insert_or_update_user_details (
    _user_id UUID,
    _contact_number VARCHAR,
    _address_street VARCHAR,
    _address_city VARCHAR,
    _address_county VARCHAR,
    _address_post_code VARCHAR
) RETURNS VOID AS $$
BEGIN 
    -- Check if the user_id exists in the users table
    IF NOT EXISTS (SELECT 1 FROM users WHERE users.user_id = _user_id) THEN
        RAISE EXCEPTION 'User ID % does not exist in the users table', _user_id;
    END IF;

    -- Insert or update the details into the user_details table if the user exists
    -- If the user already has details stored, update them
    INSERT INTO user_details (
        user_id,
        contact_number,
        address_street,
        address_city,
        address_county,
        address_post_code
    ) VALUES (
        _user_id,
        _contact_number,
        _address_street,
        _address_city,
        _address_county,
        _address_post_code
    ) ON CONFLICT (user_id) DO UPDATE SET
        contact_number = EXCLUDED.contact_number,
        address_street = EXCLUDED.address_street,
        address_city = EXCLUDED.address_city,
        address_county = EXCLUDED.address_county,
        address_post_code = EXCLUDED.address_post_code;
END;
$$ LANGUAGE plpgsql;