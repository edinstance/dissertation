
-- This table stores the details of the users.
CREATE TABLE IF NOT EXISTS user_details (
    user_id UUID PRIMARY KEY,
    contact_number VARCHAR(20),
    address_street VARCHAR(255),
    address_city VARCHAR(100),
    address_county VARCHAR(100),
    address_post_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);