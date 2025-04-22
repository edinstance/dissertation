
-- This trigger is used to update the last_updated_date column of the winning bids table
CREATE TRIGGER set_updated_date_winning_bids
BEFORE UPDATE ON winning_bids
FOR EACH ROW
EXECUTE FUNCTION update_last_updated_date();
