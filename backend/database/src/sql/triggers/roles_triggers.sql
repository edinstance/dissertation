-- This trigger is used to update the last_updated_date column of the roles table
CREATE TRIGGER set_updated_date_roles
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
