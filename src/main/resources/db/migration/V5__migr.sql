CREATE TABLE IF NOT EXISTS person
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255),
    gender BIT,
    age    INT
);