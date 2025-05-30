-- This trigger is used to update the last_updated_date column of the admin roles table
CREATE TRIGGER set_updated_date_admin_roles
BEFORE UPDATE ON admin_roles
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
