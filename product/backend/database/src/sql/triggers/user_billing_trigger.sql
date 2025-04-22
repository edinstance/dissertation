-- This trigger is used to update the last_updated_date column of the user billing table
CREATE TRIGGER set_updated_date_user_billing
BEFORE UPDATE ON user_billing
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
