CREATE TABLE IF NOT EXISTS user_table
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INT
);

INSERT INTO user_table (name, age)
VALUES ('Ivan', 30),
       ('Alisa', 25),
       ('Andrii', 35);