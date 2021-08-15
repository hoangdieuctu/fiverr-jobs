CREATE TABLE "user"
(
    "id"            VARCHAR(20) PRIMARY KEY,
    "username"      VARCHAR(20) NOT NULL,
    "password"      VARCHAR(20) NOT NULL,
    "scope"         VARCHAR(20) NOT NULL,
    "full_name"     VARCHAR(20) NOT NULL
);