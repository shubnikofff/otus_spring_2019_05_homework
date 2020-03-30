DROP TABLE IF EXISTS lib_authors;
CREATE TABLE lib_authors
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS lib_genres;
CREATE TABLE lib_genres
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS lib_books;
CREATE TABLE lib_books
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    title    VARCHAR2(1024) NOT NULL UNIQUE,
    genre_id BIGINT         NOT NULL
);

ALTER TABLE lib_books
    ADD FOREIGN KEY (genre_id) REFERENCES lib_genres (id);

DROP TABLE IF EXISTS lib_books_authors;
CREATE TABLE lib_books_authors
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT unique_book_id_author_id UNIQUE (book_id, author_id)
);

ALTER TABLE lib_books_authors
    ADD FOREIGN KEY (book_id) REFERENCES lib_books (id);

ALTER TABLE lib_books_authors
    ADD FOREIGN KEY (author_id) REFERENCES lib_authors (id);

DROP TABLE IF EXISTS lib_comments;
CREATE TABLE lib_comments
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR2(255) NOT NULL,
    text     TEXT          NOT NULL,
    book_id  BIGINT        NOT NULL
);

ALTER TABLE lib_comments
    ADD FOREIGN KEY (book_id) REFERENCES lib_books (id);
