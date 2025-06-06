-- This creates a permission for reading user data.
INSERT INTO
    permissions (
        permission_id,
        resource_id,
        action_id,
        description
    )
SELECT
    gen_random_uuid (),
    (
        SELECT
            resource_id
        FROM
            resources
        WHERE
            resource = 'USERS'
    ),
    (
        SELECT
            action_id
        FROM
            actions
        WHERE
            action = 'READ'
    ),
    'Allows reading user information';