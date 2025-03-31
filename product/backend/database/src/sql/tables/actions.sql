-- This tables stores the actions
CREATE TABLE IF NOT EXISTS actions (
    action_id UUID PRIMARY KEY,
    action VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
