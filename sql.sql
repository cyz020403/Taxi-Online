create table admin
(
    id        int unsigned auto_increment
        primary key,
    user_name varchar(32)                 not null,
    password  varchar(32)                 not null,
    balance   decimal(10, 2) default 0.00 not null,
    constraint user_name
        unique (user_name)
)
    collate = utf8mb4_general_ci;

create table demo
(
    passenger_time datetime    null,
    passenger_id   varchar(32) not null
        primary key
)
    collate = utf8mb4_general_ci;

create table driver
(
    id           int unsigned auto_increment
        primary key,
    user_name    varchar(32)                 not null,
    password     varchar(32)                 not null,
    balance      decimal(10, 2) default 0.00 not null,
    allowed_work tinyint(1)     default 1    not null,
    constraint user_name
        unique (user_name)
)
    collate = utf8mb4_general_ci;

create table allow_work
(
    id        int unsigned auto_increment
        primary key,
    driver_id int unsigned not null,
    admin_id  int unsigned not null,
    constraint fk_allow_work_1
        foreign key (driver_id) references driver (id),
    constraint fk_allow_work_2
        foreign key (admin_id) references admin (id)
)
    collate = utf8mb4_general_ci;

create table car
(
    id      int unsigned auto_increment
        primary key,
    plate   varchar(32)                     not null,
    manager varchar(32)                     not null,
    lat     decimal(10, 6) default 0.000000 not null,
    lng     decimal(10, 6) default 0.000000 not null,
    constraint plate_2
        unique (plate),
    constraint fk_1
        foreign key (manager) references driver (user_name)
)
    collate = utf8mb4_general_ci;

create table passenger
(
    id        int unsigned auto_increment
        primary key,
    user_name varchar(32)                     not null,
    password  varchar(32)                     not null,
    balance   decimal(6, 2)  default 0.00     not null,
    lat       decimal(10, 6) default 0.000000 not null,
    lng       decimal(10, 6) default 0.000000 not null,
    constraint user_name
        unique (user_name)
)
    collate = utf8mb4_general_ci;

create table settle
(
    id          int unsigned auto_increment
        primary key,
    admin_id    int unsigned               not null,
    driver_id   int unsigned               not null,
    order_count int           default 0    not null,
    sum_price   decimal(6, 2) default 0.00 not null,
    settle_time timestamp                  null,
    constraint fk_settle_1
        foreign key (driver_id) references driver (id),
    constraint fk_settle_2
        foreign key (admin_id) references admin (id)
);

create table work
(
    id         int unsigned auto_increment
        primary key,
    driver_id  int unsigned         not null,
    car_id     int unsigned         not null,
    is_working tinyint(1) default 0 not null,
    is_trans   tinyint(1) default 0 not null,
    start_time timestamp            null,
    end_time   timestamp            null,
    constraint fk_work_1
        foreign key (driver_id) references driver (id),
    constraint fk_work_2
        foreign key (car_id) references car (id)
)
    collate = utf8mb4_general_ci;

create table taxi_order
(
    id           int unsigned auto_increment
        primary key,
    work_id      int unsigned                    not null,
    passenger_id int unsigned                    not null,
    start_lng    decimal(10, 6) default 0.000000 not null,
    start_lat    decimal(10, 6) default 0.000000 not null,
    end_lng      decimal(10, 6) default 0.000000 not null,
    end_lat      decimal(10, 6) default 0.000000 not null,
    start_time   timestamp                       null,
    end_time     timestamp                       null,
    remark       varchar(100)                    null,
    price        decimal(6, 2)  default 0.00     not null,
    is_settled   tinyint(1)     default 0        not null,
    is_transing  tinyint(1)                      null,
    is_reserve   tinyint(1)     default 0        not null,
    constraint fk_taxi_order_3
        foreign key (work_id) references work (id),
    constraint fk_taxi_order_4
        foreign key (passenger_id) references passenger (id)
)
    collate = utf8mb4_general_ci;

