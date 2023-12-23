CREATE DATABASE taskbook CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;

USE taskbook;

CREATE TABLE tasks (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  initial_date DATETIME NOT NULL,
  final_date DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE utf8mb3_general_ci;
