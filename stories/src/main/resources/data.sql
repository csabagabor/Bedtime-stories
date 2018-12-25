INSERT INTO user (id, username, password_hash, email) VALUES (1, 'user1', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"a.gmail.com");
INSERT INTO user (id, username, password_hash, email) VALUES (2, 'user2', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"b.gmail.com");
INSERT INTO user (id, username, password_hash, email) VALUES (3, 'user3', '$2a$04$/RMi/GKsWIsP36pyBXfl2.V.pXX7HCnntSoHsNQ1AdRLwTu6Rg4nS',"c.gmail.com");

INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 4);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 5);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 5);


INSERT INTO author (id, name) VALUES (1, "Grimm");
INSERT INTO author (id, name) VALUES (2, "Noname");


INSERT INTO genre (id, type) VALUES (1, "fairytale");
INSERT INTO genre (id, type) VALUES (2, "short");


INSERT INTO tale (id, title, description, genre_id, author_id, date_added, rating, nr_rating)
 VALUES (1, "short story","description", 1,1,"2018-01-05", 5.0, 100);