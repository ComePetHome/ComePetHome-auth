create table users(
    id bigint AUTO_INCREMENT,
    user_id varchar(255),
    password varchar(255),
    access_token varchar(255),
    refresh_token varchar(255),
    nick_name varchar(100),
    image_url varchar(255),
    create_at date,
    update_at date,
    name varchar(255),
    phone_number varchar(255),
    primary key (id)
);

insert into users (user_id, password, nick_name, name, phone_number) values ('ryu', '1234', '닉네임', '류현우', '010-1234-1234');
