CREATE TABLE IF NOT EXISTS merged_data
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    age  INT,
    total_age INT
);

INSERT INTO merged_data (name, age)
SELECT name, age
FROM user_table;

INSERT INTO merged_data (total_age)
SELECT total_age
FROM aggregated_data;

DROP TABLE user_table;
DROP TABLE aggregated_data;


