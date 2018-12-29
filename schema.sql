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