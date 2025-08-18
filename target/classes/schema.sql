CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    last_login TIMESTAMP NOT NULL,
    token VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE phones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number BIGINT NOT NULL,
    citycode INT NOT NULL,
    contrycode INT NOT NULL,
    user_id VARCHAR(36),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);