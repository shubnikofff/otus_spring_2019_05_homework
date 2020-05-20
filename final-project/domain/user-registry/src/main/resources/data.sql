INSERT INTO PROFILES(ID, FIRST_NAME, LAST_NAME, EMAIL)
VALUES (1, 'Martin', 'Fowler', 'martin@martinfowler.com'),
       (2, 'Bob', 'Martin', 'admin@unclebob.com');

INSERT INTO USERS(ID, USERNAME, `PASSWORD`, PROFILE_ID)
VALUES (1, 'fowler', 'password', 1),
       (2, 'martin', 'password', 2);

INSERT INTO ROLES(ID, NAME)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (1, 1),
       (1, 2),
       (2, 2);
