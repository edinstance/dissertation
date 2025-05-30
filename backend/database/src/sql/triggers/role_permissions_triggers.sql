-- This trigger is used to update the last_updated_date column of the role permissions table
CREATE TRIGGER set_updated_date_role_permissions
BEFORE UPDATE ON role_permissions
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
