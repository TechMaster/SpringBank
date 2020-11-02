INSERT INTO `roles` VALUES (1,'BANK_OPERATOR'), (2,'CUSTOMER');
INSERT INTO login.users (user_id,active,email,last_name,name,password,user_name) VALUES (1,1,'hoa9x3@gmail.com','Đoàn','Hòa','$2a$10$Z.rZc5dVrmb40lDF65majuTQnVS4EtspF3GjaOpji6qbN0oqZhcOS','hoaronal');
INSERT INTO user_role (user_id,role_id) VALUES (1,1);