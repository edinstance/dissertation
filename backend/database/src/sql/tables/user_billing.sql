-- This table stores the user billing info
CREATE TABLE IF NOT EXISTS user_billing (
    user_id UUID PRIMARY KEY,
    account_id VARCHAR,
    customer_id VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
