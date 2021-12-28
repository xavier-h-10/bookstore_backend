drop schema if exists bookstore;
create schema bookstore;
use bookstore;

/* Table structure for book_image */
create table `book_image`
(
    `id`    int unsigned auto_increment not null,
    `image` blob                        not null,
    primary key (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for book_description */
create table `book_description`
(
    `id`          int unsigned auto_increment not null,
    `description` varchar(1000)               not null,
    primary key (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for book */
drop table if exists `book`;
create table `book`
(
    `id`          int unsigned auto_increment not null,
    `title`       varchar(63)                 not null,
    `author`      varchar(63)                 not null,
    `price`       decimal(10, 2)              not null,
    `isbn`        varchar(15)                 not null,
    `inventory`   mediumint unsigned          not null,
    `image_id`    int unsigned                not null,
    `descript_id` int unsigned                not null,
    `type`        varchar(15)                 not null, # 历史类/文学类...
    `enabled`     tinyint(1) unsigned         not null default 1,
    index (title),
    index (author),
    primary key (`id`),
    foreign key (`image_id`) references book_image (`id`),
    foreign key (`descript_id`) references book_description (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for user_auth */
drop table if exists `user_auth`;
create table `user_auth`
(
    `id`       int unsigned auto_increment not null,
    `username` varchar(31)                 not null,
    `password` varchar(31)                 not null,
    `type`     tinyint unsigned            not null default 0, /* 0: user  1: admin */
    primary key (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for user */
drop table if exists `user`;
create table `user`
(
    `id`       int unsigned auto_increment   not null,
    `nickname` varchar(31)                   not null,
    `email`    varchar(63)                   not null,
    `enabled`  tinyint(1) unsigned default 1 not null, /* true or false */
    `auth_id`  int unsigned                  not null,
    index (`nickname`),
    primary key (`id`),
    foreign key (`auth_id`) references user_auth (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for order */
drop table if exists `order`;
create table `order`
(
    `id`      int unsigned auto_increment not null,
    `user_id` int unsigned                not null,
    `time`    datetime                    not null,
    `price`   decimal(10, 2)              not null,
    index (time, user_id),
    primary key (`id`),
    foreign key (`user_id`) references `user` (`id`)
) engine = InnoDB
  charset = utf8;

/* Table structure for order_item */
drop table if exists `order_item`;
create table `order_item`
(
    `id`       int unsigned auto_increment not null,
    `book_id`  int unsigned                not null,
    `order_id` int unsigned                not null,
    `amount`   mediumint unsigned          not null,
    `price`    decimal(10, 2)              not null,
    index (`order_id`),
    primary key (`id`),
    foreign key (`order_id`) references `order` (`id`),
    foreign key (`book_id`) references `book` (`id`)
) ENGINE = InnoDB
  CHARSET = utf8;

/* Table structure for cart_item */
drop table if exists `cart_item`;
create table `cart_item`
(
    `id`      int unsigned auto_increment   not null,
    `user_id` int unsigned                  not null,
    `book_id` int unsigned                  not null,
    `amount`  mediumint unsigned            not null,
    `active`  tinyint(1) unsigned default 1 not null,
    index (`user_id`, `active`),
    PRIMARY KEY (`id`),
    foreign key (`user_id`) references `user` (`id`),
    foreign key (`book_id`) references `book` (`id`)
) engine = InnoDB
  charset = utf8;


