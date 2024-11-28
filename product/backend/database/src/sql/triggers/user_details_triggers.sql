
-- This trigger is used to update the last_updated_date column of the user_details table
CREATE TRIGGER set_updated_date_user_details
BEFORE UPDATE ON user_details
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
