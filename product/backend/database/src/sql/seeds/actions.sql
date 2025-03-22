-- This seeds some default actions into the actions table
INSERT INTO
    actions (action_id, action, description)
VALUES
    (
        gen_random_uuid (),
        'read',
        'This action allows the user to read data'
    );

INSERT INTO
    actions (action_id, action, description)
VALUES
    (
        gen_random_uuid (),
        'write',
        'This action allows the user to write data'
    );