ALTER DATABASE comepethome CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    password VARCHAR(255),
    access_token VARCHAR(255),
    refresh_token VARCHAR(255),
    nick_name VARCHAR(255),
    image_url VARCHAR(255),
    create_at DATE,
    update_at DATE,
    name VARCHAR(255),
    phone_number VARCHAR(255)
);

insert into users (user_id, password, nick_name, name, phone_number) values ('ryu', '1234', '닉네임', '류현우', '010-1234-1234');
