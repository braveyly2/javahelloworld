create table if not exists user
(
  id bigint unsigned auto_increment primary key,
  phone varchar(255) charset utf8 null,
  email varchar(255) charset utf8 null,
  wx_id varchar(255) charset utf8 null,
  wx_nickname varchar(255) charset utf8 null,
  password varchar(255) charset utf8 null,
  mark varchar(255) charset utf8 null
);