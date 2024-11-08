
-- This trigger is used to update the last_updated_date column of the users table
CREATE TRIGGER set_updated_date_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
