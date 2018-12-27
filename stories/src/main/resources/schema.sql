drop table if exists role;
drop table if exists user;
drop table if exists user_roles;
drop table if exists author;
drop table if exists genre;
drop table if exists tale;
drop table if exists user_tales;
drop table if exists rating;

create table rating (id bigint not null auto_increment, user_id bigint not null, tale_id bigint not null,rating bigint, primary key (id)) engine=MyISAM;
create table role (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=MyISAM;
create table user (id bigint not null auto_increment, password_hash varchar(255), username varchar(255),email varchar(255), primary key (id)) engine=MyISAM;
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=MyISAM;
create table user_tales (user_id bigint not null, tale_id bigint not null, primary key (user_id, tale_id)) engine=MyISAM;

create table author (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=MyISAM;
create table genre (id bigint not null auto_increment, type varchar(255), primary key (id)) engine=MyISAM;
create table tale (id bigint not null auto_increment, title varchar(255), author_id bigint not null, genre_id bigint not null, description
  varchar(35500),date_added varchar(255), rating float, nr_rating bigint, primary key (id)) engine=MyISAM;


alter table tale add constraint FKrx9tnx5kyftwctq3uqv159t5j foreign key (author_id) references author (id);
alter table tale add constraint FKk8jp92aican3vyldn4jjle0vi foreign key (genre_id) references genre (id);

alter table user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);


alter table user_tales add constraint FK53taf3o560tr704q4645eidct foreign key (tale_id) references tale (id);

