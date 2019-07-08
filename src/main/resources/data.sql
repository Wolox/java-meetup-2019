DROP TABLE IF EXISTS students;

CREATE TABLE students (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL
);

INSERT INTO students (first_name, last_name, career) VALUES
  ('Bran', 'Stark', 'Magia e ilusionismo'),
  ('Daenerys', 'Targaryen', 'Ciencias políticas'),
  ('Tyrion', 'Lanninster', 'Ciencias económicas');