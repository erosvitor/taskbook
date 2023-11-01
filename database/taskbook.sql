USE taskbook;

CREATE TABLE tasks (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  initial_date DATETIME NOT NULL,
  final_date DATETIME NOT NULL,
  PRIMARY KEY (id)
);

