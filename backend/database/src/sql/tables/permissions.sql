-- This tables stores the permissions
CREATE TABLE IF NOT EXISTS permissions (
    permission_id UUID PRIMARY KEY,
    resource_id UUID NOT NULL,
    action_id UUID NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (resource_id, action_id),
    FOREIGN KEY (resource_id) REFERENCES resources (resource_id),
    FOREIGN KEY (action_id) REFERENCES actions (action_id)
);
