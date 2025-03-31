-- This trigger is used to update the last_updated_date column of the resources table
CREATE TRIGGER set_updated_date_resources
BEFORE UPDATE ON resources
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
