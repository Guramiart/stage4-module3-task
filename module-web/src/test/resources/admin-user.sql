INSERT INTO role (id, name) VALUES(default, 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO users (id, username, password)
    VALUES(default, 'TestAdmin', '$2a$10$5waLhm1usEcTMwQcoJQBmOhmoZi1tftwXyfol6E9CiGZaj9kNH0Bm')
    ON CONFLICT (username) DO NOTHING;
INSERT INTO user_role (user_id, role_id) VALUES(1, 1);