
-- This trigger is used to update the last_updated_date column of the actions table
CREATE TRIGGER set_updated_date_actions
BEFORE UPDATE ON actions
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
