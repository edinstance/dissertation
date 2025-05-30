
CREATE VIEW user_with_details AS
SELECT
    u.user_id,
    u.email,
    u.name,
    u.status,
    COALESCE(ud.contact_number, '') AS contact_number,
    COALESCE(ud.address_street, '') AS address_street,
    COALESCE(ud.address_city, '') AS address_city,
    COALESCE(ud.address_county, '') AS address_county,
    COALESCE(ud.address_post_code, '') AS address_post_code
FROM
    users u
LEFT JOIN
    user_details ud ON u.user_id = ud.user_id;