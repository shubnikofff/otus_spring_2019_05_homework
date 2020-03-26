INSERT INTO LIB_AUTHORS (ID, NAME)
VALUES (1, 'John Ronald Reuel Tolkien'),
       (2, 'Fyodor Mikhailovich Dostoevsky'),
       (3, 'Leo Tolstoy'),
       (4, 'Ilia Ilf'),
       (5, 'Eugene Petrov'),
       (6, 'Erich Gamma'),
       (7, 'Richard Helm'),
       (8, 'Ralph Johnson'),
       (9, 'John Vlissides');

INSERT INTO LIB_GENRES(ID, NAME)
VALUES (1, 'Fantasy'),
       (2, 'Novel'),
       (3, 'Drama'),
       (4, 'Adventure'),
       (5, 'Computer science');

INSERT INTO LIB_BOOKS(ID, TITLE, GENRE_ID)
VALUES (1, 'Lord of the rings', 1),
       (2, 'Crime and Punishment', 2),
       (3, 'War and Peace', 2),
       (4, 'Anna Karenina', 3),
       (5, 'Golden calf', 4),
       (6, 'Design Patterns', 5);

INSERT INTO LIB_BOOKS_AUTHORS(BOOK_ID, AUTHOR_ID)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 3),
       (5, 4),
       (5, 5),
       (6, 6),
       (6, 7),
       (6, 8),
       (6, 9);
