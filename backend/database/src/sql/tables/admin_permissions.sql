-- This table stores the permissions granted to the admins.
CREATE TABLE IF NOT EXISTS admin_permissions (
    admin_id UUID,
    permission_id UUID,
    grant_type VARCHAR(10) NOT NULL DEFAULT 'GRANT',
    PRIMARY KEY (admin_id, permission_id),
    FOREIGN KEY (admin_id) REFERENCES admins (user_id),
    FOREIGN KEY (permission_id) REFERENCES permissions (permission_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);