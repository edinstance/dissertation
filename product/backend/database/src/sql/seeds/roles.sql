-- This seeds a default admin role into the roles table
INSERT INTO
    roles (role_id, role_name, description)
VALUES
    (
        gen_random_uuid (),
        'Default Admin',
        'This role is for a default admin'
    );