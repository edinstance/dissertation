-- This table stores the roles assigned to the admins.
CREATE TABLE IF NOT EXISTS admin_roles (
    admin_id UUID,
    role_id UUID,
    PRIMARY KEY (admin_id, role_id),
    FOREIGN KEY (admin_id) REFERENCES admins (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);