DROP DATABASE hairstory;
/*
DROP TABLE MASTER_MENU;
DROP TABLE ADMIN
DROP TABLE CUSTOMER
DROP TABLE SHOP
DROP TABLE BRANCH
DROP TABLE STAFF
DROP TABLE DAY_OFF_SCHEDULE
DROP TABLE MENU_FOR_STAFF
DROP TABLE PAYMENT
DROP TABLE MENU_FOR_PAYMENT
DROP TABLE RESERVATION
DROP TABLE MENU_FOR_RESERVATION
DROP TABLE MASTER_MENU
DROP TABLE REVIEW
*/
CREATE DATABASE hairstory;

CREATE TABLE MASTER_MENU(
	menu_id INT AUTO_INCREMENT, 
	menu_name VARCHAR(50),
	show_flag CHAR(1) DEFAULT "1",
	PRIMARY KEY (menu_id));

CREATE TABLE ADMIN (
	admin_id VARCHAR(15), 
	password VARCHAR(20), 
	f_name VARCHAR(30), 
	l_name VARCHAR(30), 
	role CHAR(2), -- 01:Super Admin, 02:Shop Admin, 03:Branch Admin
	phone_num VARCHAR(12),
	email VARCHAR(50), 
	date_of_birth DATE, 
	reg_date DATETIME, 
	update_date DATETIME,	
	show_flag CHAR(1) DEFAULT "1",
	PRIMARY KEY (admin_id));

CREATE TABLE CUSTOMER(
	customer_id VARCHAR(15), 
	password VARCHAR(20), 
	f_name VARCHAR(30), 
	l_name VARCHAR(30), 	
	phone_num VARCHAR(12),
	email VARCHAR(50), 
	date_of_birth DATE, 
	reg_date DATETIME, 
	update_date DATETIME,	
	show_flag CHAR(1) DEFAULT "1",
	PRIMARY KEY (customer_id));

CREATE TABLE SHOP(
	shop_id INT, 
	shop_name VARCHAR(30), 
	business_reg_num VARCHAR(30), -- ??
	admin_id VARCHAR(15), 
	reg_date DATETIME,
	PRIMARY KEY (shop_id),
	FOREIGN KEY (admin_id) REFERENCES ADMIN(admin_id));

CREATE TABLE BRANCH(
	branch_id INT, 
	branch_name varchar(30), 
	address varchar(100), 
	open_time TIME, 
	close_time TIME, 
	shop_id INT,
	admin_id VARCHAR(15),
	reg_date DATETIME,
	PRIMARY KEY (branch_id),
	FOREIGN KEY (shop_id) REFERENCES SHOP(shop_id),
	FOREIGN KEY (admin_id) REFERENCES ADMIN(admin_id));

CREATE TABLE STAFF(
	staff_id INT, 
	f_name VARCHAR(30), 
	l_name VARCHAR(30),  
	gender CHAR(1), -- F:Female, M:Male, O:Other
	phone_num VARCHAR(12),
	email VARCHAR(50), 
	date_of_birth DATE, 
	branch_id INT, 
	reg_date DATETIME, 
	update_date DATETIME,
	PRIMARY KEY (staff_id),
	FOREIGN KEY (branch_id) REFERENCES BRANCH(branch_id));

CREATE TABLE DAY_OFF_SCHEDULE(
	staff_id INT, 
	day_off_date DATE, 
	start_time TIME, 
	end_time TIME, 
	reg_date DATETIME, 
	PRIMARY KEY (staff_id, day_off_date),
	FOREIGN KEY (staff_id) REFERENCES STAFF(staff_id));

CREATE TABLE MENU_FOR_STAFF(
	staff_id INT,
	menu_id INT, 
	service_time INT(2), 
	price INT,
	PRIMARY KEY(staff_id, menu_id),
	FOREIGN KEY (staff_id) REFERENCES STAFF(staff_id),
	FOREIGN KEY (menu_id) REFERENCES MASTER_MENU(menu_id));

CREATE TABLE PAYMENT(
	payment_id INT(10), 
	payment_amount INT, 
	tip_amount INT, 
	payment_type CHAR(2), -- 01:cash, 02:debit card, 03:credit card
	customer_id INT, 
	reg_date DATETIME, 
	update_date DATETIME,
	PRIMARY KEY(payment_id),
	FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id));

CREATE TABLE MENU_FOR_PAYMENT(
	payment_id INT(10), 
	reservation_id INT(10), 
	menu_id INT, 
	service_time INT(2), 
	price INT,
	PRIMARY KEY (payment_id, reservation_id, menu_id),
	FOREIGN KEY (payment_id) REFERENCES PAYMENT(payment_id),
	FOREIGN KEY (reservation_id) REFERENCES RESERVATION(reservation_id),
	FOREIGN KEY (menu_id) REFERENCES MASTER_MENU(menu_id));


CREATE TABLE RESERVATION(
	reservation_id INT(10), 
	reservation_date DATE, 
	start_time TIME, 
	end_time TIME, 
	total_price INT, 
	reservation_type CHAR(2), -- O1: Online, 02: Walk in, 03: cancel
	staff_id INT, 
	customer_id INT, 
	reg_date DATETIME, 
	update_date DATETIME,
	PRIMARY KEY (reservation_id),
	FOREIGN KEY (staff_id) REFERENCES STAFF(staff_id),
	FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id));


CREATE TABLE MENU_FOR_RESERVATION(
	reservation_id INT(10), 
	menu_id INT, 
	service_time INT(2), 
	price INT,
	PRIMARY KEY (reservation_id, menu_id),
	FOREIGN KEY (reservation_id) REFERENCES RESERVATION(reservation_id),
	FOREIGN KEY (menu_id) REFERENCES MASTER_MENU(menu_id));




CREATE TABLE REVIEW(
	review_id INT(10), 
	content TEXT, 
	review_point INT(1), 
	branch_id INT, 
	staff_id INT, 
	customer_id INT, 
	reg_date DATETIME,
	PRIMARY KEY (review_id),
	FOREIGN KEY (branch_id) REFERENCES BRANCH(branch_id),
	FOREIGN KEY (staff_id) REFERENCES STAFF(staff_id),
	FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id));
