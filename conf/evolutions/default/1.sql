# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "nodes" ("id" SERIAL NOT NULL PRIMARY KEY,"parent_id" INTEGER NOT NULL,"type" VARCHAR(254) NOT NULL,"title" VARCHAR(254) NOT NULL,"desc" VARCHAR(254) NOT NULL);
create table "tils" ("id" SERIAL NOT NULL PRIMARY KEY,"node_id" INTEGER NOT NULL,"user_id" VARCHAR(254) NOT NULL,"date" TIMESTAMP NOT NULL,"title" VARCHAR(254) NOT NULL,"desc" VARCHAR(254) NOT NULL);
create table "tracks" ("id" SERIAL NOT NULL PRIMARY KEY,"node_id" INTEGER NOT NULL,"user_id" VARCHAR(254) NOT NULL,"start" TIMESTAMP NOT NULL,"end" TIMESTAMP NOT NULL);
create table "users" ("id" SERIAL NOT NULL PRIMARY KEY,"email" VARCHAR(254) NOT NULL,"username" VARCHAR(254) NOT NULL,"password" VARCHAR(254) NOT NULL);

# --- !Downs

drop table "users";
drop table "tracks";
drop table "tils";
drop table "nodes";

