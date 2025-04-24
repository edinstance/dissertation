-- This table stores the winning bids.
CREATE TABLE IF NOT EXISTS winning_bids (
    bid_id UUID PRIMARY KEY,
    item_id UUID,
    seller_id UUID,
    buyer_id UUID,
    final_price DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items (item_id),
    FOREIGN KEY (seller_id) REFERENCES users (user_id),
    FOREIGN KEY (buyer_id) REFERENCES users (user_id)
);
