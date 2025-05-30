-- This trigger is used to update the last_updated_date column of the permissions table
CREATE TRIGGER set_updated_date_permissions
BEFORE UPDATE ON permissions
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
