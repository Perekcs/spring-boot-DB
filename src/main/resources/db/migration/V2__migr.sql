CREATE TABLE IF NOT EXISTS aggregated_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_age INT
);

INSERT INTO aggregated_data (total_age)
SELECT SUM(age) FROM user_table;