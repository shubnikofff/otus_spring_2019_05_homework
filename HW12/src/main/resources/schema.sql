DROP TABLE IF EXISTS Users;
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR2(20) UNIQUE NOT NULL,
    password VARCHAR2(50)        NOT NULL
)
