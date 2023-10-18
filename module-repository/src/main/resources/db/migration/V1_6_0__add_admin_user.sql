INSERT INTO roles (id, name) VALUES(default, 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO users (id, username, password)
    VALUES(default, 'admin', '$2a$10$BppoGW1vDnY0o51QzTwMDe9KYiZ5AS5Ou9lFDQgDiHSqTdBFv0hFi')
    ON CONFLICT (username) DO NOTHING;
INSERT INTO users_roles (user_id, role_id) VALUES(1, 1);