INSERT INTO USERS(ID, USERNAME, PASSWORD, FULL_NAME)
VALUES (1, 'admin', 'password', 'System administrator'),
       (2, 'fowler', 'password', 'Martin Fowler'),
       (3, 'tolkien', 'password', 'John R. R. Tolkien');

INSERT INTO ROLES(ID, NAME)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (1, 1),
       (2, 2),
       (3, 2);

INSERT INTO ACL_SID(ID, PRINCIPAL, SID)
VALUES (1, 1, 'admin'),
       (2, 1, 'fowler'),
       (3, 1, 'tolkien'),
       (4, 0, 'ROLE_ADMIN'),
       (5, 0, 'ROLE_USER');

INSERT INTO ACL_CLASS(ID, CLASS)
VALUES (1, 'ru.otus.domain.Comment');

INSERT INTO ACL_OBJECT_IDENTITY(ID, OBJECT_ID_CLASS, OBJECT_ID_IDENTITY, PARENT_OBJECT, OWNER_SID, ENTRIES_INHERITING)
VALUES (1, 1, '1', NULL, 2, 0),
       (2, 1, '2', NULL, 3, 0),
       (3, 1, '3', NULL, 3, 0),
       (4, 1, '4', NULL, 2, 0);

INSERT INTO ACL_ENTRY(ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING, AUDIT_SUCCESS, AUDIT_FAILURE)
VALUES (1, 1, 2, 3, 1, 1, 1),
       (1, 2, 3, 0, 1, 1, 1),
       (2, 1, 2, 0, 1, 1, 1),
       (2, 2, 3, 3, 1, 1, 1),
       (3, 1, 2, 0, 1, 1, 1),
       (3, 2, 3, 3, 1, 1, 1),
       (4, 1, 2, 3, 1, 1, 1),
       (4, 2, 3, 0, 1, 1, 1);
