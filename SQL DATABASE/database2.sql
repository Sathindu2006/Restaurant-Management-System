CREATE DATABASE Shop;
USE Shop;

CREATE TABLE menu (
    menu_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
  
    price DECIMAL(8,2) NOT NULL
);

INSERT INTO menu (menu_id,item_name,price) VALUES
(1,'Coffee',400.00),
(2,'Rotti', 250.00),
(3,'Tea', 90.00),
(4,'Ice Cream', 80.00);

SELECT*FROM menu;

CREATE TABLE employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

INSERT INTO employee(username, password) VALUES
('admin', '1234'),
('john', 'abcd');

SELECT*FROM employee;