
INSERT INTO users (id, name, email, password, created, modified, last_login, token, is_active)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    'Martin Giraldo',
    'martin.giraldo@email.com',
    'medellin',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    '123e4567-e89b-12d3-a456-426614174000',
    TRUE
);

INSERT INTO phones (number, citycode, contrycode, user_id)
VALUES ('1234567', '1', '57', '550e8400-e29b-41d4-a716-446655440000');
