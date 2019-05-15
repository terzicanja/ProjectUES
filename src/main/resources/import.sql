INSERT INTO users (username, password, name, lastname) VALUES ('a', '$2a$10$yS7pAhT6AVZUhW27So8NA.CQGybqWN4e5g8XV0/QQvw07lDqyH81y', 'Anja', 'prezime a');
INSERT INTO users (username, password, name, lastname) VALUES ('b', '$2a$10$yS7pAhT6AVZUhW27So8NA.CQGybqWN4e5g8XV0/QQvw07lDqyH81y', 'Bbb', 'prezime b');

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_COMMENTATOR');

INSERT INTO categories (name) VALUES ('nesto');
INSERT INTO categories (name) VALUES ('drugo');

INSERT INTO language (name) VALUES ('jezik');
INSERT INTO language (name) VALUES ('drugi jezik');

INSERT INTO books (author, title, year, category_id, language_id) VALUES ('aaaaaa', 'knjigaaa', 2010, 1, 1);
INSERT INTO books (author, title, year, category_id, language_id) VALUES ('cccccccc', 'knjigaaa 222', 2003, 1, 1);

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);