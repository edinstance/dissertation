-- This table stores the details of the admins.
CREATE TABLE IF NOT EXISTS admins (
    user_id UUID PRIMARY KEY,
    is_super_admin BOOLEAN DEFAULT FALSE,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
    created_by UUID,
    last_updated_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (created_by) REFERENCES admins (user_id),
    FOREIGN KEY (last_updated_by) REFERENCES admins (user_id)
);