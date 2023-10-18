CREATE TABLE IF NOT EXISTS users_roles (
    User_Id bigint NULL,
    Role_Id bigint NULL,

    CONSTRAINT fk_user_role FOREIGN KEY (User_Id) REFERENCES users(Id),
    CONSTRAINT fk_role_user FOREIGN KEY (Role_Id) REFERENCES roles(Id)
);