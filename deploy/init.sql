USE mydatabase;
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    quota_id VARCHAR(36),
    last_login_time_utc TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE user_quota (
    id VARCHAR(36) PRIMARY KEY,
    blocked BOOLEAN,
    remain_quota INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);