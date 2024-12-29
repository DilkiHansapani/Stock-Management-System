CREATE DATABASE IF NOT EXISTS stock_management_system;
USE stock_management_system;

CREATE USER IF NOT EXISTS 'stock_management_system_user'@'%' IDENTIFIED BY 'pwd@sms';

GRANT ALL PRIVILEGES ON stock_management_system.* TO 'stock_management_system_user'@'%';

FLUSH PRIVILEGES;
