-- This table contains the resources.
CREATE TABLE IF NOT EXISTS resources (
    resource_id UUID PRIMARY KEY,
    resource VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
