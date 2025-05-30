
-- This trigger is used to update the last_updated_date column of the items table
CREATE TRIGGER set_updated_date_items
BEFORE UPDATE ON items
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
