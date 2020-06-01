-- profiles

DROP TABLE IF EXISTS profiles;
CREATE TABLE profiles
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR2(50) NOT NULL,
    last_name  VARCHAR2(50) NOT NULL,
    email      VARCHAR2(30) NOT NULL
);

-- users

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR2(20) NOT NULL UNIQUE,
    password VARCHAR2(50) NOT NULL,
    profile_id BIGINT
);

ALTER TABLE users
    ADD FOREIGN KEY (profile_id) REFERENCES profiles (id);

-- roles

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR2(50) NOT NULL UNIQUE
);

-- users_roles

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT unique_user_id_role_id UNIQUE (user_id, role_id)
);

ALTER TABLE users_roles
    ADD FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE users_roles
    ADD FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE;

