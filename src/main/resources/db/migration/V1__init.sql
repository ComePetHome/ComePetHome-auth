create table users(
    id bigint generated by default as identity,
    user_id varchar(255),
    password varchar(255),
    name varchar(100),
    phone_number varchar(20),
    primary key (id)
);

insert into users (user_id, password, name, phone_number) values ('ryu', '1234', '류현우', '010-1234-1234');
