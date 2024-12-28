CREATE DATABASE IF NOT EXIST stock_managment_system;
USE stock_managment_system;
CREATE USER 'stock_management_system_user'@'%' IDENTIFIED BY 'pwd@sms';
GRANT ALL PRIVILEGES ON stock_managment_system.* TO 'stock_management_system_user'@'%';

