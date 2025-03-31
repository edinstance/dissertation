-- This trigger is used to update the last_updated_date column of the admins table
CREATE TRIGGER set_updated_date_admins
BEFORE UPDATE ON admins
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
