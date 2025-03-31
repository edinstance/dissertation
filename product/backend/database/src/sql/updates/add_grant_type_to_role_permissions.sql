-- Add a grant_type column to role_permissions table
ALTER TABLE role_permissions
ADD COLUMN grant_type VARCHAR(10) NOT NULL DEFAULT 'GRANT';