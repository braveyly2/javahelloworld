create table if not exists user
(
  id bigint unsigned auto_increment
    primary key,
  name varchar(255) charset utf8 null,
  password varchar(255) charset utf8 null,
  mark varchar(255) charset utf8 null
);