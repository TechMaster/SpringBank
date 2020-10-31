DROP TABLE IF EXISTS USER;
CREATE TABLE USER (id bigint auto_increment PRIMARY KEY, full_name varchar(255) NOT NULL,
email varchar(255) NULL,  mobile varchar(255) NULL, photo varchar(255) NULL
, created_date timestamp NULL)
