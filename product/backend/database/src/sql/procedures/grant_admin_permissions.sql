-- This procedure grants an admin a permission
CREATE OR REPLACE PROCEDURE grant_admin_permission(
    _admin_id UUID,
    _performing_admin_id UUID,
    _action VARCHAR,
    _resource VARCHAR
) AS
$$
DECLARE
    _action_id     UUID;
    _resource_id   UUID;
    _permission_id UUID;
BEGIN
    PERFORM check_user_and_admin_status(_admin_id, _performing_admin_id);

    SELECT action_id INTO _action_id FROM actions WHERE action = _action;

    IF (_action_id IS NULL) THEN
        RAISE EXCEPTION 'Action ID does not exist for %1 action', _action;
    END IF;

    SELECT resource_id INTO _resource_id FROM resources WHERE resource = _resource;

    IF (_resource_id IS NULL) THEN
        RAISE EXCEPTION 'Resource ID does not exist for %1 resource', _resource;
    END IF;

    SELECT permission_id
    INTO _permission_id
    FROM permissions
    WHERE action_id = _action_id
      AND resource_id = _resource_id;


    IF (_permission_id IS NULL) THEN
        RAISE EXCEPTION 'Permission ID does not exist for %1 resource id and %2 action id', _resource_id, _action_id;
    END IF;

    INSERT INTO admin_permissions (admin_id, permission_id)
    VALUES (_admin_id, _permission_id)
    ON CONFLICT (admin_id, permission_id) DO NOTHING;

END;
$$ LANGUAGE plpgsql;