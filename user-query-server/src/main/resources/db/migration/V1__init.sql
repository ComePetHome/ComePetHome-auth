CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    password VARCHAR(255),
    access_token VARCHAR(255),
    refresh_token VARCHAR(255),
    nick_name VARCHAR(255),
    image_url VARCHAR(255),
    create_at DATETIME,
    update_at DATETIME,
    name VARCHAR(255),
    phone_number VARCHAR(255)
);

