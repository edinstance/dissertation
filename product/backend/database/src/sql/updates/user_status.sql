-- This adds a status column to the users table
ALTER TABLE users
ADD COLUMN status VARCHAR(255) NOT NULL DEFAULT 'PENDING';