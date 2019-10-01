DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID    INT PRIMARY KEY,
    TITLE VARCHAR(255)
);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID   INT PRIMARY KEY,
    NAME VARCHAR(255)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   INT PRIMARY KEY,
    NAME VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS_AUTHORS;
CREATE TABLE BOOKS_AUTHORS
(
    BOOK_ID   INT,
    AUTHOR_ID INT,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS (ID),
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS (ID)
);

DROP TABLE IF EXISTS BOOKS_GENRES;
CREATE TABLE BOOKS_GENRES
(
    BOOK_ID  INT,
    GENRE_ID INT,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS (ID),
    FOREIGN KEY (GENRE_ID) REFERENCES GENRES (ID)
);
