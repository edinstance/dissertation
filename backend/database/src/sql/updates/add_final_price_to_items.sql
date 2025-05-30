-- Add a final_price column to items table
ALTER TABLE items
ADD COLUMN final_price DECIMAL(10, 2) DEFAULT NULL;