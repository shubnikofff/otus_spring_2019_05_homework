-- users

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    username  VARCHAR2(20)  NOT NULL UNIQUE,
    password  VARCHAR2(50)  NOT NULL,
    full_name VARCHAR2(100) NOT NULL
);

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

-- acl_sid

DROP TABLE IF EXISTS acl_sid;
CREATE TABLE acl_sid
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    principal TINYINT(1)    NOT NULL,
    sid       VARCHAR2(100) NOT NULL,
    CONSTRAINT unique_principal_sid UNIQUE (principal, sid)
);

-- acl_class

DROP TABLE IF EXISTS acl_class;
CREATE TABLE acl_class
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    class VARCHAR2(255) NOT NULL UNIQUE
);

-- acl_object_identity

DROP TABLE IF EXISTS acl_object_identity;
CREATE TABLE acl_object_identity
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    object_id_class    BIGINT       NOT NULL,
    object_id_identity VARCHAR2(20) NOT NULL,
    parent_object      BIGINT(20) DEFAULT NULL,
    owner_sid          BIGINT(20) DEFAULT NULL,
    entries_inheriting TINYINT(1)   NOT NULL,
    CONSTRAINT unique_object_id_class_object_id_identity UNIQUE (object_id_class, object_id_identity)
);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

-- acl_entry

DROP TABLE IF EXISTS acl_entry;
CREATE TABLE acl_entry
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    acl_object_identity BIGINT     NOT NULL,
    ace_order           INT        NOT NULL,
    sid                 BIGINT     NOT NULL,
    mask                INT(11)    NOT NULL,
    granting            TINYINT(1) NOT NULL,
    audit_success       TINYINT(1) NOT NULL,
    audit_failure       TINYINT(1) NOT NULL,
    CONSTRAINT unique_acl_object_identity_ace_order UNIQUE (acl_object_identity, ace_order)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);
