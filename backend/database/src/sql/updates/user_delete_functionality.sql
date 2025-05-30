-- This adds a is deleted column to the users table
ALTER TABLE users
ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

