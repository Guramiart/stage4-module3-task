CREATE TABLE IF NOT EXISTS authors (
    Id BIGSERIAL PRIMARY KEY NOT NULL,
    Name VARCHAR(100) UNIQUE NOT NULL
);