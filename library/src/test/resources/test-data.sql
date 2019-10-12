INSERT INTO AUTHORS(ID, NAME)
VALUES (1, 'Author #1'),
       (2, 'Author #2'),
       (3, 'Author #3'),
       (4, 'Author #4'),
       (5, 'Author #5'),
       (6, 'Author #6'),
       (7, 'Author #7');

INSERT INTO GENRES (ID, NAME)
VALUES (1, 'Genre #1'),
       (2, 'Genre #2'),
       (3, 'Genre #3'),
       (4, 'Genre #4'),
       (5, 'Genre #5'),
       (6, 'Genre #6');

INSERT INTO BOOKS (ID, TITLE, GENRE_ID)
VALUES (1, 'Book #1', 1),
       (2, 'Book #2', 4),
       (3, 'Book #3', 4),
       (4, 'Book #4', 5),
       (5, 'Book #5', 2),
       (6, 'Book #6', 6);

INSERT INTO BOOKS_AUTHORS(BOOK_ID, AUTHOR_ID)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 3),
       (5, 4),
       (5, 5),
       (6, 6),
       (6, 7);
