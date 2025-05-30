-- This seeds some default actions into the actions table
INSERT INTO
    resources (resource_id, resource, description)
VALUES
    (
        gen_random_uuid (),
        'ADMINS',
        'This resource is for the admin information'
    );

INSERT INTO
    resources (resource_id, resource, description)
VALUES
    (
        gen_random_uuid (),
        'USERS',
        'This resource is for the user information'
    );