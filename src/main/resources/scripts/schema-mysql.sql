CREATE TABLE if not exists PLANET (
   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   climate VARCHAR(100) NOT NULL,
   terrain VARCHAR(100) NOT NULL,
   films_quantity INT NOT NULL
);