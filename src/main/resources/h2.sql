
SET MODE POSTGRESQL;



CREATE TABLE IF NOT EXISTS USER (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(20),
  username   VARCHAR(20),
  password   VARCHAR(20) NOT NULL DEFAULT ''
);

INSERT INTO USER (name, username, password) VALUES ('vlad', 'admin', 'admin');


CREATE TABLE IF NOT EXISTS Gun (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(255) NOT NULL,
  caliber DOUBLE       NOT NULL,
  price   DOUBLE NOT NULL
);


CREATE TABLE IF NOT EXISTS Bucket (
  id      INT,
  gun_id  ARRAY
);

CREATE TABLE IF NOT EXISTS Orders (
  id             INT,
  gun_order_list ARRAY
);





INSERT INTO Bucket (id, gun_id) VALUES (1, (1,2,3));




INSERT INTO Gun (name, caliber, price) VALUES ('Kolt', 11.52, 523.33);
INSERT INTO Gun (name, caliber, price) VALUES ('Beretta', 6.35, 600.54);
INSERT INTO Gun (name, caliber, price) VALUES ('Glock', 9.0, 5000);
INSERT INTO Gun (name, caliber, price) VALUES ('AKM-47', 7.62, 10000);
INSERT INTO Gun (name, caliber, price) VALUES ('AK-74', 5.45, 5678.5678);
