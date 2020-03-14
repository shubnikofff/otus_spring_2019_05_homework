DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR2(20) NOT NULL UNIQUE,
    password VARCHAR2(50) NOT NULL
);

DROP TABLE IF EXISTS acl_sid;
CREATE TABLE IF NOT EXISTS acl_sid
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    principal TINYINT(1)    NOT NULL UNIQUE,
    sid       VARCHAR2(100) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS acl_class;
CREATE TABLE IF NOT EXISTS acl_class
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    class VARCHAR2(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS acl_entry;
CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    acl_object_identity BIGINT     NOT NULL UNIQUE,
    ace_order           INT        NOT NULL UNIQUE,
    sid                 BIGINT     NOT NULL,
    mask                INT(11)    NOT NULL,
    granting            TINYINT(1) NOT NULL,
    audit_success       TINYINT(1) NOT NULL,
    audit_failure       TINYINT(1) NOT NULL
);

DROP TABLE IF EXISTS acl_object_identity;
CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    object_id_class    BIGINT     NOT NULL UNIQUE,
    object_id_identity BIGINT     NOT NULL UNIQUE,
    parent_object      BIGINT(20) DEFAULT NULL,
    owner_sid          BIGINT(20) DEFAULT NULL,
    entries_inheriting TINYINT(1) NOT NULL
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
