-- This function inserts a new row into the items table if the item_id does not exist, 
-- otherwise it updates the existing row 
CREATE OR REPLACE FUNCTION insert_or_update_item (
    _item_id UUID,
    _name VARCHAR,
    _description TEXT,
    _is_active BOOLEAN,
    _ending_time TIMESTAMP,
    _price DECIMAL,
    _stock INT,
    _category VARCHAR,
    _images JSONB,
    _seller_id UUID
) RETURNS TABLE (
    item_id UUID,
    name VARCHAR,
    description TEXT,
    is_active BOOLEAN,
    ending_time TIMESTAMP,
    price DECIMAL,
    stock INT,
    category VARCHAR,
    images JSONB,
    seller_id UUID
) AS $$
BEGIN 
    -- Check if the seller_id exists in the users table
    IF NOT EXISTS (SELECT 1 FROM users WHERE users.user_id = _seller_id) THEN
        RAISE EXCEPTION 'Seller ID % does not exist in the users table', _seller_id;
    END IF;

    -- If _item_id is provided, update the existing item
    IF _item_id IS NOT NULL THEN
        RETURN QUERY
        UPDATE items SET
            name = _name,
            description = _description,
            is_active = COALESCE(_is_active, TRUE),
            ending_time = _ending_time,
            price = _price,
            stock = _stock,
            category = _category,
            images = _images,
            seller_id = _seller_id
        WHERE items.item_id = _item_id
        RETURNING items.item_id, items.name, items.description, items.is_active, items.ending_time, items.price, items.stock, items.category, items.images, items.seller_id;
    ELSE
        -- Insert a new item if _item_id is NULL
        RETURN QUERY
        INSERT INTO items (
            name,
            description,
            is_active,
            ending_time,
            price,
            stock,
            category,
            images,
            seller_id
        ) VALUES (
            _name,
            _description,
            COALESCE(_is_active, TRUE),
            _ending_time,
            _price,
            _stock,
            _category,
            _images,
            _seller_id
        )
        RETURNING items.item_id, items.name, items.description, items.is_active, items.ending_time, items.price, items.stock, items.category, items.images, items.seller_id;
    END IF;
END;
$$ LANGUAGE plpgsql;