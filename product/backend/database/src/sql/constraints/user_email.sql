-- Drop the unique contstraint on email column
ALTER TABLE users DROP CONSTRAINT users_email_key;
-- Create a partial unique index on email column so that users can recreate accounts with the same email
CREATE UNIQUE INDEX IF NOT EXISTS unique_email ON users (email) WHERE is_deleted = FALSE;