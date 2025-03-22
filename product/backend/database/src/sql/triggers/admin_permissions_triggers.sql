-- This trigger is used to update the last_updated_date column of the admin permissions table
CREATE TRIGGER set_updated_date_admin_permissions
BEFORE UPDATE ON admin_permissions
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
