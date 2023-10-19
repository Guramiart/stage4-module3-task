INSERT INTO roles (Id, Name) VALUES(default, 'ROLE_ADMIN') ON CONFLICT (Name) DO NOTHING;
INSERT INTO users (Id, Username, Password)
    VALUES(default, 'admin', '$2a$10$BppoGW1vDnY0o51QzTwMDe9KYiZ5AS5Ou9lFDQgDiHSqTdBFv0hFi')
    ON CONFLICT (Username) DO NOTHING;
INSERT INTO users_roles (User_Id, Role_Id) VALUES(1, 1);