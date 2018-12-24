INSERT INTO user (id, username, password_hash, email) VALUES (1, 'user1', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"a.gmail.com");
INSERT INTO user (id, username, password_hash, email) VALUES (2, 'user2', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"b.gmail.com");
INSERT INTO user (id, username, password_hash, email) VALUES (3, 'user3', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"c.gmail.com");

INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 4);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 5);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 5);