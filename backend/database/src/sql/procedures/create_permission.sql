-- This procedure creates a permission
CREATE OR REPLACE PROCEDURE create_permission(
    _permission_description VARCHAR,
    _action VARCHAR,
    _action_description VARCHAR,
    _resource VARCHAR,
    _resource_description VARCHAR
) AS
$$
DECLARE
    _action_id         UUID;
    _resource_id       UUID;
BEGIN

    INSERT INTO actions (action_id, action, description)
    VALUES (gen_random_uuid(), _action, _action_description)
    ON CONFLICT (action)
        DO NOTHING;

    SELECT action_id INTO _action_id FROM actions WHERE action = _action;

    IF _action_id IS NULL THEN
        RAISE EXCEPTION 'Failed to find or create action ID for action name: %', _action;
    END IF;

    INSERT INTO resources (resource_id, resource, description)
    VALUES (gen_random_uuid(), _resource, _resource_description)
    ON CONFLICT (resource)
        DO NOTHING;

    SELECT resource_id INTO _resource_id FROM resources WHERE resource = _resource;

    IF _resource_id IS NULL THEN
        RAISE EXCEPTION 'Failed to find or create resource ID for resource name: %', _resource;
    END IF;


    INSERT INTO permissions (permission_id, resource_id, action_id, description)
    VALUES (gen_random_uuid(), _resource_id, _action_id, _permission_description)
    ON CONFLICT (resource_id, action_id)
        DO NOTHING;

    RAISE INFO 'Created new permission with action %1 and resource %2', _action, _resource;

END;
$$ LANGUAGE plpgsql;
