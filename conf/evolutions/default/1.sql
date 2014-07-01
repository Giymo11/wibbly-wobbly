# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "users" ("id" SERIAL NOT NULL PRIMARY KEY,"email" VARCHAR(254) NOT NULL,"username" VARCHAR(254) NOT NULL,"password" VARCHAR(254) NOT NULL);

# --- !Downs

drop table "users";

