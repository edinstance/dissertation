-- This seed gives the default admin role some permissions.
INSERT INTO
    role_permissions (role_id, permission_id)
VALUES
    (
        (
            SELECT
                role_id
            FROM
                roles
            WHERE
                role_name = 'Default Admin'
        ),
        (
            SELECT
                permission_id
            FROM
                permissions
            WHERE
                description = 'Allows reading user information'
        )
    );