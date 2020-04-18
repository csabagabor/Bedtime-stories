# Bedtime stories website with Spring Boot REST API + PostgreSQL hosted on Heroku + JWT based authentication
### About
- Full example can be seen [here](https://bedtime-stories.herokuapp.com/index.html). Note that because the backend runs on a free dyno on Heroku, it may take some time to load
- This is a small application which serves a new bedtime story for kids every day.
- Users can create accounts and can login with their accounts.
- Every story can be rated by users and all the average ratings are stored in a `PostgreSQL` database. The frontend communicates with the backend with a `REST` api.



### Features
- JWT token based authentication(2 roles - admin and user)
- passwords are hashed with Bcrypt 
- uses Hibernate ORM Framework
- `Spring Boot` backend with REST API hosted on HEROKU
- frontend in `JS` + `jQuery` + `Bootstrap 4`
- `PostgreSQL` database

### Actors and rights

#### Regular user:

- visiting the website

- logging in

- logging out

- registering to the website

- modifying information from personal account

- reading a story

- giving a rating to a story

- choosing a favorite story

- removing story from favorites

- choosing a story based on the genre

- choosing a story based on the upload date

- viewing the ranking of stories based on the ratings

-  submitting new stories

#### Admin
- has all the rights of a regular user

- adding a new story to be displayed

- modifying the story

- modifying information about a story

- deleting a story

### How to build/run
1. first clone the project
2. after creating a heroku account, follow [this](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku) guide to add a new Heroku repo
3. create a new PostgreSQL database with `heroku addons:create heroku-postgresql` in `Heroku CLI`
4. to create the tables write `heroku pg:psql` in the `CLI` and then copy this query:
```sql
drop table if exists role;
drop table if exists users;
drop table if exists user_roles;
drop table if exists author;
drop table if exists genre;
drop table if exists tale;
drop table if exists user_tales;
drop table if exists rating;

create sequence rating_seq;

create table rating (id bigint not null default nextval ('rating_seq'), user_id bigint not null, tale_id bigint not null,rating bigint, primary key (id)) ;
create sequence role_seq;

create table role (id bigint not null default nextval ('role_seq'), description varchar(255), name varchar(255), primary key (id)) ;
create sequence user_seq;

create table users (id bigint not null default nextval ('user_seq'), password_hash varchar(255), username varchar(255),email varchar(255), primary key (id)) ;
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) ;
create table user_tales (user_id bigint not null, tale_id bigint not null, primary key (user_id, tale_id)) ;

create sequence author_seq;

create table author (id bigint not null default nextval ('author_seq'), name varchar(255), primary key (id)) ;
create sequence genre_seq;

create table genre (id bigint not null default nextval ('genre_seq'), type varchar(255), primary key (id)) ;
create sequence tale_seq;

create table tale (id bigint not null default nextval ('tale_seq'), title varchar(255), author_id bigint not null, genre_id bigint not null, description
  varchar(35500),date_added varchar(255), rating double precision, nr_rating bigint, primary key (id)) ;


alter table tale add constraint FKrx9tnx5kyftwctq3uqv159t5j foreign key (author_id) references author (id);
alter table tale add constraint FKk8jp92aican3vyldn4jjle0vi foreign key (genre_id) references genre (id);

alter table user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references users (id);


alter table user_tales add constraint FK53taf3o560tr704q4645eidct foreign key (tale_id) references tale (id);
```
5. Now, you have your table but it is empty. You have to add data to it.
```sql
INSERT INTO users (username, password_hash, email) VALUES ('admin', '$2a$04$gERWL367O5zl.NDG1livYOpcPhbXVVE35dZEz6dD8uJlNljotX6Gq','admin@gmail.com');--12345678 - change THIS!!!
INSERT INTO users (username, password_hash, email) VALUES ('user1', '$2a$04$gERWL367O5zl.NDG1livYOpcPhbXVVE35dZEz6dD8uJlNljotX6Gq','user2@gmail.com');--12345678
INSERT INTO users (username, password_hash, email) VALUES ('user2', '$2a$04$S7pErSB6NPX5pwE5fjDDb.Q.aqNjQzz7s/FOmtB5XEXB1Q82tkyIG','user3@gmail.com');--87654321

INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (2, 4);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 5);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 5);


INSERT INTO genre (id, type) VALUES (1, 'fairy-tale');
INSERT INTO genre (id, type) VALUES (2, 'fiction');
INSERT INTO genre (id, type) VALUES (3, 'short-story');
INSERT INTO genre (id, type) VALUES (4, 'fable');
INSERT INTO genre (id, type) VALUES (5, 'young-adult-fiction');
INSERT INTO genre (id, type) VALUES (6, 'fantasy');

INSERT INTO author (id, name) VALUES (1, 'Henrique Figora');
INSERT INTO author (id, name) VALUES (2, 'Brenda Wright ');
INSERT INTO author (id, name) VALUES (3, 'Lorena Rey');
INSERT INTO author (id, name) VALUES (4, 'Brian Chometsky');
INSERT INTO author (id, name) VALUES (5, 'Tracy Martin');
INSERT INTO author (id, name) VALUES (6, 'Ken Huculak');
INSERT INTO author (id, name) VALUES (7, 'H. P. Lovecraft');
INSERT INTO author (id, name) VALUES (8, 'William Shakespeare');
INSERT INTO author (id, name) VALUES (9, 'Lord Byron');
INSERT INTO author (id, name) VALUES (10, 'Robert Ervin Howard');
INSERT INTO author (id, name) VALUES (11, 'Edgar Allan Poe');
INSERT INTO author (id, name) VALUES (12, 'Arthur Conan Doyle');
INSERT INTO author (id, name) VALUES (13, 'Emily Dickinson');
INSERT INTO author (id, name) VALUES (14, 'John Masefield');
INSERT INTO author (id, name) VALUES (15, 'John Donne');
INSERT INTO author (id, name) VALUES (16, 'Gilbert Keith Chesterton');
INSERT INTO author (id, name) VALUES (17, 'Rudyard Kipling');
INSERT INTO author (id, name) VALUES (19, 'Alfred, Lord Tennyson');
INSERT INTO author (id, name) VALUES (20, 'Charles Dickens');
INSERT INTO author (id, name) VALUES (21, 'Mohandas K. Gandhi');
INSERT INTO author (id, name) VALUES (22, 'Oscar Wilde');
INSERT INTO author (id, name) VALUES (23, 'William Topaz McGonagall');
INSERT INTO author (id, name) VALUES (24, 'William Blake');
INSERT INTO author (id, name) VALUES (26, 'John Keats');
INSERT INTO author (id, name) VALUES (27, 'Ambrose Bierce');
INSERT INTO author (id, name) VALUES (28, 'Saki');
INSERT INTO author (id, name) VALUES (29, 'T. S. Eliot');
INSERT INTO author (id, name) VALUES (30, 'Ernest Hemingway');
INSERT INTO author (id, name) VALUES (31, 'Robert Louis Stevenson');
INSERT INTO author (id, name) VALUES (32, 'Leo Tolstoy');
INSERT INTO author (id, name) VALUES (33, 'Mark Twain');
INSERT INTO author (id, name) VALUES (34, 'Friedrich Nietzsche');
INSERT INTO author (id, name) VALUES (35, 'H. G. Wells');
INSERT INTO author (id, name) VALUES (36, 'Robert Frost');
INSERT INTO author (id, name) VALUES (37, 'Bret Harte');
INSERT INTO author (id, name) VALUES (38, 'President Abraham Lincoln');
INSERT INTO author (id, name) VALUES (39, 'Marcus Tullius Cicero');
INSERT INTO author (id, name) VALUES (40, 'O. Henry');
INSERT INTO author (id, name) VALUES (41, 'James Joyce');
INSERT INTO author (id, name) VALUES (42, 'Jack London');
INSERT INTO author (id, name) VALUES (43, 'Henry Wadsworth Longfellow');
INSERT INTO author (id, name) VALUES (44, 'Aristotle');
INSERT INTO author (id, name) VALUES (45, 'William Butler Yeats');
INSERT INTO author (id, name) VALUES (46, 'Jane Austen');
INSERT INTO author (id, name) VALUES (47, 'Emily Brontë');
INSERT INTO author (id, name) VALUES (48, 'Robert Burns');
INSERT INTO author (id, name) VALUES (49, 'Lewis Carroll');
INSERT INTO author (id, name) VALUES (50, 'Horace');
INSERT INTO author (id, name) VALUES (51, 'Virginia Woolf');
INSERT INTO author (id, name) VALUES (52, 'Percy Bysshe Shelley');
INSERT INTO author (id, name) VALUES (53, 'Agatha Christie');
INSERT INTO author (id, name) VALUES (54, 'D. H. Lawrence');
INSERT INTO author (id, name) VALUES (55, 'Theodore Kaczynski');
INSERT INTO author (id, name) VALUES (56, 'Rabindranath Tagore');
INSERT INTO author (id, name) VALUES (57, 'Augustine of Hippo');
INSERT INTO author (id, name) VALUES (58, 'Alexander Pope');
INSERT INTO author (id, name) VALUES (59, 'Bram Stoker');
INSERT INTO author (id, name) VALUES (60, 'Voltaire');
INSERT INTO author (id, name) VALUES (61, 'Dante Alighieri');
INSERT INTO author (id, name) VALUES (62, 'Florence Earle Coates');
INSERT INTO author (id, name) VALUES (63, 'Ovid');
INSERT INTO author (id, name) VALUES (64, 'Francis Bacon');
INSERT INTO author (id, name) VALUES (65, 'Honoré de Balzac');
INSERT INTO author (id, name) VALUES (66, 'William Henry Davies');
INSERT INTO author (id, name) VALUES (67, 'Fyodor Dostoevsky');
INSERT INTO author (id, name) VALUES (68, 'John Milton');
INSERT INTO author (id, name) VALUES (69, 'Seneca');
INSERT INTO author (id, name) VALUES (70, 'George Bernard Shaw');
INSERT INTO author (id, name) VALUES (71, 'Francis Scott Fitzgerald');
INSERT INTO author (id, name) VALUES (72, 'Victor Hugo');
INSERT INTO author (id, name) VALUES (73, 'Niccolò Machiavelli');
INSERT INTO author (id, name) VALUES (74, 'Arthur Schopenhauer');
INSERT INTO author (id, name) VALUES (75, 'Haile Selassie');
INSERT INTO author (id, name) VALUES (76, 'Napoleon Bonaparte');
INSERT INTO author (id, name) VALUES (77, 'John Clare');
INSERT INTO author (id, name) VALUES (78, 'Samuel Taylor Coleridge');
INSERT INTO author (id, name) VALUES (79, 'H. H. Matthews');
INSERT INTO author (id, name) VALUES (80, 'Beatrix Potter');
INSERT INTO author (id, name) VALUES (81, 'John Ruskin');
INSERT INTO author (id, name) VALUES (82, 'William Wordsworth');
INSERT INTO author (id, name) VALUES (83, 'Charlotte Brontë');
INSERT INTO author (id, name) VALUES (84, 'Julius Caesar');
INSERT INTO author (id, name) VALUES (85, 'President John F. Kennedy');
INSERT INTO author (id, name) VALUES (86, 'Sir John Alexander Macdonald');
INSERT INTO author (id, name) VALUES (87, 'Wilfred Owen');
INSERT INTO author (id, name) VALUES (88, 'Clark Ashton Smith');
INSERT INTO author (id, name) VALUES (89, 'Jules Verne');
INSERT INTO author (id, name) VALUES (90, 'Brothers Grimm');
INSERT INTO author (id, name) VALUES (91, 'Tracy Martin');
INSERT INTO author (id, name) VALUES (92, 'Matt Delorme');
INSERT INTO author (id, name) VALUES (93, 'Duran Horse');

INSERT INTO tale (title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ( 'The Forest Princess',E'A long time ago, in a big beautiful forest there lived many animals. The animals in the forest\r\nwere happy and they lived a wonderful life. One reason that the animals were happy was\r\nbecause in the forest there also lived a little princess. The little princess had long curly,\r\nblonde hair. Do you know what her name was? It might surprise you but her name was\r\nMarina! Yes, Marina, a beautiful name for a beautiful little princess.\r\nEvery morning she would go to the park near the river to play with her friends. Mrs. Rabbit,\r\nMr. Skunk and the very young deer were her best friends.\r\nOne day they were playing together. They were having so much fun that they lost track of\r\nthe time. The sun went down and it became dark. The little animals were afraid to go home\r\nalone.\r\nPrincess Marina had an idea. She said, \'Why don\'t you all come to my Grandpa\'s home with\r\nme. Grandpa doesn\'t mind it when I bring my friends to his place.\'\r\nWhen Marina and her friends arrived at her grandpa\'s house her invited them in. He gave\r\nthem all the treats that they could eat. After they ate they all played games.\r\nAfter a time Grandpa in a deep loud voice said, \'It\'s time for bed now.\' Grandpa gathered\r\nall the friends around and told them a bedtime story. Before long everyone was fast asleep.\r\nIn the morning it was safe for the animals to go home.\r\nEveryday Marina and her friends still play but now they make sure that they go home before\r\nit gets dark.', 1,1,'2018-12-20', 4.0, 1);

INSERT INTO tale ( title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ('Always Listen',E'Many moons ago there was a folk tale passed down from generation to generation. The\r\nstory is about the Cho-Cho Man or the Boogie Man. It all began with a young boy named\r\nDakota.\r\nDakota\'s mother had asked him to gather some firewood for the evening fire. The nights\r\nwere getting cooler. Instead of doing the chore right away, Dakota figured he had enough\r\nsunlight left to do two things. He would gather the firewood after he played with his friends\r\nMorgan, Joey, and Bo.\r\nNot long after, his mother began to wonder why Dakota never came with any firewood.\r\nNight had fallen fast and it was too hard to see the firewood.\r\nDakota realized what had happened. He told his friends about his predicament and they said\r\nthat they would help their friend. Soon after, the young boys were gathering wood in the\r\ndark.\r\nDakota\'s mother was worried that something had happened. She went looking for Dakota.\r\nNot long after, she heard the boys. She had mixed feelings. She was happy to see Dakota\r\nbut she was also mad that he hadn\'t listened to his mother.\r\nShe wanted to teach Dakota and his friends a lesson so she sneaked into the bush very\r\nquietly. Then she picked up a twig and threw it. The twig hit the ground making noise. It\r\ncaught the boys\' attention. She threw another broken twig in the opposite direction. The\r\nboys jumped. The mother had a hard time holding back her laughter and a muffled moan\r\nsounded through her fingers.\r\nThe boys were definitely scared. But Dakota was the brave one. He ventured forward. The\r\nmother moaned again; she threw another twig. The boys were transfixed. Once more the\r\nmother moaned in a low and deep voice, \'Listen to your mother.\'\r\nThe boys dropped everything and ran. The mother could not hold back her laughter and the\r\nboys, hearing this, scurried home even faster.\r\nAfter the mother had picked up some firewood, she proceeded home, where she found\r\nDakota in tears.\r\nDakota exclaimed, \'Mother, Mother I\'m sorry I didn\'t listen to you.\'\r\nHis mother held him with love and she knew that he was truly sorry. From then on when\r\nDakota felt like not listening, the mother would mention, \'The boogie-man will scare you if\r\nyou misbehave.\'', 2,2,'2018-12-21', 1.5, 100);

INSERT INTO tale (title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ('The Five Little Stars ',E'She closed her eyes and made a wish. She wished she could have five little stars. She\r\nwished they were hers to hold in her hands, to keep close to her heart and to care for and\r\nto love forever.\r\nWhen she opened her eyes, something caught her eye. There were five twinkling stars all in\r\na row. She knew these would be hers when the time was right. They would be there\r\nwaiting.\r\nTime passed and when the time was right her first little star came to her. She knew this star\r\nwas a boy so she named him Gabriel. He was so beautiful. She was so happy. This was the\r\nmost wonderful feeling that she had ever known. This little star was hers to keep. She took\r\nhim in her hands and took care of him. She loved him with all of her heart. Gabriel gave her\r\nso much joy in her life; she loved every minute of it.\r\nSome time passed and the second little star came to her. She also knew this star would be\r\na boy; she named him Javier. And as with the first star, she took him in her hands and she\r\nheld him close to her heart. She took care of him and loved him as much as she did her first\r\nstar. Javier was such a beautiful star. She couldn\'t believe her eyes.\r\nThe young woman was so thrilled to have two stars to love with all of her heart. Each time\r\nshe thought of them her eyes filled with tears of joy.\r\nSome time after, another little star came to her. This one was also a boy so she named him\r\nJames. She took her new little star in her hands and she held him close to her heart. She\r\ncared for him and loved him as much as her first two little stars. He was so gorgeous she\r\ncouldn\'t believe this little star was also hers to keep.\r\nNow she had three little stars to care for. She loved them all so very much. She watched\r\nthem grow and she admired them every day. She would take them out and show them to\r\nthe world. As she enjoyed and took care of them, the years went by.\r\nShe was in shock when the last two little stars that she had wished for came to her at the\r\nsame time. She could hardly believe her good fortune. She had always wanted all boys but\r\nwhen she had made her wish so long ago she had thought that this was too much to ask.\r\nThe two new tiny stars were named Andrew and Adam Her other little stars: Gabriel, Javier\r\nand James were so overwhelmed with joy that they wanted to help all the time.\r\nThe lady\'s heart was filled with joy. She took her new little stars in her hands. She held\r\nthem close to her heart and she cared for them. These two new stars were so tiny and\r\nbeautiful.\r\nShe had so much love in her. She had enough to give all of them the love they needed.\r\nThey were all part of her life. They were hers to keep and love forever.\r\nAs her five little stars grew, she would always tell them that she loved them all the same\r\nand she always would. She promised that she would be there for them whenever they would \r\nneed her, just as they were there for her when she needed them. Her wish had come true;\r\nhaving five little stars was a joy.\r\nNow she knows deep inside that when the time is right she would have to let go. But even\r\nat that time she will always hold them close to her heart and she\'ll be there when they need\r\na hug. ', 1,3,'2018-12-22', 4.7, 99);

INSERT INTO tale ( title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ('John Goes to the City',E'Once up on a time there was mischievous raccoon named John. He lived with a man name\r\nJim. John lived with Jim in a big house in the country.\r\nOne day Jim had to go to the city for some business. He put on his tall, tall hat. Jim always\r\nwore a tall, tall hat wherever he went.\r\nJim knew that if he left John alone he would most likely get into things that he shouldn\'t.\r\nJim didn\'t like to leave John alone and Jim knew he had to take John with him to the city.\r\nJim and John got in Jim\'s car and drove to the city. John was very excited because he hadn\'t\r\nbeen to the big city before. The closer the car got to the city, the more exited John got.\r\nFinally they came to the hotel. Jim told John to stay in the car. Jim talked to the man at the\r\nfront desk and he booked a room for two.\r\nJim went to see how John was doing. When Jim went to the car, the car was gone. Jim was\r\nfrightened. He didn\'t know what to do.\r\nA policeman came walking by just at that time. Jim was happy to see him. He ran to the\r\npoliceman and said; \'My car is missing and John is in it.\'\r\nThe policeman said, \'I just saw a yellow car go rolling down the hill and there wasn\'t a\r\ndriver!\'\r\nJim was worried about what had happened to John and the car. The policeman asked Jim\r\nwhat had happened.\r\nJim thought to himself. Then he remembered that he had just had his brakes checked and\r\nthe man had said the brakes needed some work done very soon.\r\nJust then another policeman came running. \'I just saw a yellow car go down the hill.\' Both\r\nthe policemen and Jim went running down the hill. At the bottom they saw the car resting\r\non the curb.\r\n\'Whew! Are we lucky,\' said Jim. Jim was very happy at first but he wasn\'t happy very long.\r\nHe didn\'t see John anywhere. He looked and looked but John wasn\'t anywhere to be found.\r\nThen as Jim turned around, out of the corner of his eye he saw John sitting in a tree. John\r\nlooked very scared.\r\nJim went over to the tree to help John. Another policeman said he had seen John running\r\nfrom the car after it had hit the curb.\r\nJim was happy that John wasn\'t hurt. Together they went over to see if the car was all right.\r\nJohn and Jim took the car to the garage and got the car fixed. They both went to the hotel\r\nto pick their things up.\r\nJim and John went back to the country. That was where John was most happy.', 2,4,'2018-12-23', 5.0, 5);

INSERT INTO tale (title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ('The Angel',E'Once upon a time there was an angel name Samantha. She was always getting into trouble.\r\nSamantha would make up stories and she was always getting into a lot of hot water. Some\r\nof the other angels noticed this. They started talking about how they should deal with this.\r\nOne angel said, \'Maybe we could teach her a lesson.\' They all agreed. The next day they\r\ncalled Sam into the cloud room. The boss of all the angels asked Sam to go down to earth\r\nand help someone in need.\r\nSam said, \'OK.\'\r\n\'But there is one thing you must remember,\' the boss angel said, \'You will not have any\r\npowers.\' Sam did not think this was fair. The big boss gave Sam a very nasty look and he\r\nordered her \'Go!\'\r\nDown to earth she went. She had to find someone in need of help. Samantha looked and\r\nlooked all over the place.\r\nOn one street corner she spotted two kids and a small dog. Sam came up to them and\r\nasked how they were doing; they said they were fine. Sam asked if they needed any help.\r\nOne child looked at her very strangely and said no. She moved on.\r\nNext she saw two animals wondering around. \'They look lost.\' She said to herself. She went\r\nup to them and they began to bark at her. Sam became very scared and upset. She did not\r\nknow what to do. She calmed herself down and started to talk to them. When they heard\r\nher voice they stopped barking at her. The angel felt very happy and so she helped the little\r\ndogs find their way home.\r\nSamantha was thinking to herself, \'If I could help instead of being bad all the time then I\r\nwould feel good about what I have done.\'\r\nWhen the little angel got home the boss asked her if she had learned a lesson. Sam told him\r\nthat she had. The big boss was very pleased.\r\nWhen Sam left the room, all the little angels asked her if she was all right. Sam said yes.\r\nEveryone was happy. Then, Sam and the other angels said their prayers and went to sleep.', 2,5,'2018-12-24', 4.0, 1);

INSERT INTO tale ( title, description, genre_id, author_id, date_added, rating, nr_rating)
    VALUES ('A Bedtime Story ',E'One day there was an elf walking on a forest path. As he was walking, he came upon a\r\nperson named Mat. Mat asked the elf, \' Where are you going?\'\r\nThe elf said, \'I\'m going down the path.\'\r\n\' Can I come along?\'\r\n\'Sure,\' said the elf, \' but beware, the bush is really wild, the grass is pretty high and the\r\nwater is very deep! Do you still want to go?\'\r\nMat said, \'Well, if you put that way, I guess I\'ll change my mind. So the elf left. Mat really\r\ndidn\'t trust the elf so he decided to follow him. In a very short time Mat saw that the elf had\r\ntried to trick him. The grass was not high and there weren\'t any wild bushes and the water\r\nwas not deep at all. The elf just didn\'t wanted Mat around at all.\r\nMat jumped out from behind the bushes and said to the elf, \'You tried to trick me. Why\r\ndidn\'t you let me come with you?\'\r\nThe elf felt badly and he told Mat that he was sorry. He asked Mat to come with him and the\r\ntwo kept on walking. They walked until they came to another guy named Brian. Brian asked\r\nthem where they were going.\r\nThe elf said, \'We are going for a walk in the forest. Would you like to come?\'\r\nBrian said, \' No thank you. I hear from Mat\'s friends that the bushes were wild and the\r\ngrass is pretty high and the water is pretty deep.\'\r\nThe elf replied, \'I just told him those things. But really they are not as bad as I said they\r\nare. You can ask Mat yourself. This is Mat.\'\r\nBut Brian said, \' I will pass on that.\'\r\nThe elf said, \'OK with me. We\'ll go ourselves. Don\'t say we didn\'t ask you come along for\r\nthe walk.\'\r\nMat and the elf went off into the forest. They were hungry so the began to search for some\r\nfood to eat. As they were looking, they ran into Larry. He asked them, \'Where are you\r\ngoing?\' \'We\'re going to the forest for a walk. Would you like to come with us?\' asked the\r\nelf.\r\nLarry said, \'Yes, I would like to come with you. How far are you going?\'\r\n\'Oh we are going as far as we can go.\'\r\n\'I would like to come with you. I\'m going by myself and there is not going be anyone\r\naround to talk to.\'\r\nThe elf said, \' Together we can explore many wonderful things. We three can enjoy our\r\nwalk together. It\'s much better to do things with friends than it is to do things alone.\'\r\nTo this they all agreed and so off they went together.', 3,6,'2018-12-25', 0.0, 0);

INSERT INTO tale (title, genre_id, author_id, date_added, rating, nr_rating, description)
VALUES ('A Christmas Story', 2,92,'2018-12-26', 4.0, 1, 'Once upon a Christmas holiday there was a poor family. There was a mother and father and two little ones. The family was so poor that they had no place to live except in an old car out in the back streets. People that knew of them didn"t understand their situation because they didn"t try to understand. The town people would call them names and point fingers at them. One day the two little ones went up to their mother and father and asked, " Why do we live in a car and not in a house? Why aren"t we in school?" They had tears rolling down their cheeks. Both parents sat them down, " Dear children, we do not have these things because we don"t have jobs. We have tried to hang on to our jobs. It seems the people we always help are too worried. They worry about the way people look and not the work itself. We have little money to buy new clothes. We"ve tried to ask people for help but we can"t get it. That"s why we aren"t able to have the things that others have." The children understood and didn"t ever ask again. Their parents sent them to the car to go to bed. It was the next day and it was the mother"s turn to go out and see if anyone would hire her. There was no luck at all. So the mother and father sat down and talked about what they could try to do about the problems and make their children"s dream come true. That day a rich man, by the name of Joe, came into town. Joe had so much money in his pockets that he didn"t know what to do with it all. Joe was the kind of person that couldn"t have children. He wished that one day he could have children. He thought about adopting children some day. He wasn"t quite sure what to do. Joe" had a heart of gold. He never thought about himself; he was always thinking of others and what they need. As he was driving along this night he noticed a family of four. They were picking through the garbage for food; to keep warm they stood by a flaming garbage bin. He noticed that they were wearing rags. Their faces were dirty and their hair was messed really badly. Joe walked by and thought some more about what he should do. The next day the mother and father went out to look for work. It was coming close to Christmas Eve and they wanted to try to find work once more before everything had closed. Door to door they went. Every where they went they were booted out, rejected, and doors slammed in their faces. Joe was to witness this and he felt really badly. He couldn"t believe how selfish the people who lived in town were. Joe wanted to do something about it. He thought for awhile, then he realized this family had something that he wanted and that was a family. Joe had something that they wanted. This was a home. So Joe decided he would approach them. "I have been watching you since I came into town. I have seen you and your wife trying to make a living but when you have not been able to find work, you can"t make a living that way. I"ve been thinking for couple of days. I have something you want. This is a home. You have something that I want. This is a family. I want to invite you into my home for Christmas. After Christmas if you want to be a part of my family or should I say if I could be apart of your family, we can discuss it" The family said, "Yes!" After Christmas they all agreed that they should become a family. The children began school. The parents got a job and they lived happy ever after. ');

INSERT INTO tale (title, genre_id, author_id, date_added, rating, nr_rating, description)
VALUES ('The Unicorn Named Wild One', 2,93,'2018-12-27', 5.0, 3, 'This story begins a long time ago in the 1800"s. The story takes place in a beautiful meadow. There were high mountains surrounding the meadow. The people in the village often told tales that something evil lived in the meadow. The people told tales of mysterious creatures. People from the near by village were frightened by the tales. Most would not go into the meadow. There was only one person, Tannus was her name, who did not fear the stories. Her grandmother had told her about the strange creature that the people feared. She had told Tannus that this creature was special and only people who believed would see it. Her grandmother had called it a unicorn. Tannus remembered this story. She would go to the meadow and to look for the unicorn but she had never seen it. But she believed in it. One day Tannus was in meadow walking. Once again she was looking for the unicorn. As she walked she saw something from the corner of her eye. She turned to look and there it was the unicorn. The unicorn had a beautiful white coat. Its long mane was covered with gold dust. His wings looked like they were on fire. When Tannus saw it she remembered something more from Grandmother"s story. The unicorn"s horn was said to have magic powers. Grandmother had said that if a person touches it she would receive three wishes. Tannus looked at the beautiful unicorn. She slowly walked toward it. She reached out her hand and stroked its mane gently. The unicorn knelt and allowed Tannus to get on its back. Together they rode to the village. When Tannus"s mother saw them she called out, "Get off that wild thing!" Tannus said, " No! It"s not wild mother. All that he needs is a friend. He needs some love. Mom, come for a ride with me?" Mother said, "No, Tannus! We don"t know anything about him. What does he eat?" "He eats grass. Grandmother told me about him when I was young. She told me that I could make a wish if I touch its horn and that the wish would come true. Tannus touched its horn and she made a wish. She wished for a castle. Suddenly the castle appeared. It had solid gold trim. The ballroom floor was made out of pearls. The pillars were solid gold. Tannus wished a second time. This time she wished for more unicorns so that her unicorn could be happy for the rest of his life and then there were more unicorns walking and playing in the meadow. She made a third wish and she wished for a prince for herself and the prince appeared. The prince was having so much fun with to the beautiful unicorns. He was so amazed and happy that he made his wishes for Tannus" mom. The prince wished for a king for Tannus" mom. Tannus" mother was so happy that she could have all the things that she wanted in her life. Now they could live happily ever after, and they did.');
	
```
> Note: The admin's password on the original Heroku link is not shown here for security reasons. To create your own passwords, you can use this Bcrypt tool: https://www.devglan.com/online-tools/bcrypt-hash-generator

6. Great. So far so good. Now check if everything is ok:
```sql
 select * from tale;
```
8. Change the `serverUrl` variable in the `javascipt` files located in `website/js/constants.js` folder to your own Heroku app's URL(or `localhost` if you want to test it locally).
9. You can now test the application either locally, or remotely on Heroku.
### How to add new stories/tales
There are 2 options:  
1. create a new story with an admin/user then the admin accepts the story 
2. run this SQL query:
```sql
INSERT INTO tale (title, description, genre_id, author_id, date_added, rating, nr_rating)
VALUES ( 'Title',E'Something', 1,1,'2018-12-20', 4.0, 1);
```
Note that there is a `E` in front of the description. This is used to indicate that the string is escaped in a PostgreSQL database.
> Note: Before inserting the new story into the title/description field, you have to escape some characters in it to be valid, a good tool for this is [here](https://www.freeformatter.com/json-escape.html)
### Future improvements
- for faster retrieval of data from the database, an in-memory database needs to be implemented like `Redis`
- more stories need to be added
> Note: this application uses the `jQuery` rating system made by Geoff Ellerby. You can find the repo [here](https://github.com/gellerby/jquery-emoji-ratings).
