CREATE DATABASE skeleton;

USE skeleton;

-- spring security
CREATE TABLE users
(
  id       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  username VARCHAR(32)         NOT NULL UNIQUE
  COMMENT '用户名',
  password VARCHAR(256)        NOT NULL
  COMMENT '密码',
  enabled  TINYINT(1)          NOT NULL DEFAULT 0
  COMMENT '是否启用',
  PRIMARY KEY (id)
);

CREATE TABLE authorities
(
  id        BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  username  VARCHAR(32)         NOT NULL
  COMMENT '用户名',
  authority VARCHAR(32)         NOT NULL
  COMMENT '权限',
  PRIMARY KEY (id)
);

CREATE TABLE groups
(
  id         BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  group_name VARCHAR(32)         NOT NULL UNIQUE
  COMMENT '组名',
  PRIMARY KEY (id)
);

CREATE TABLE group_members
(
  id       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  group_id BIGINT(20) UNSIGNED NOT NULL
  COMMENT '组id',
  username VARCHAR(32)         NOT NULL
  COMMENT '用户名',
  PRIMARY KEY (id)
);

CREATE TABLE group_authorities
(
  id        BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  group_id  BIGINT(20) UNSIGNED NOT NULL
  COMMENT '组id',
  authority VARCHAR(32)         NOT NULL
  COMMENT '权限',
  PRIMARY KEY (id)
);

INSERT INTO users (username, password, enabled) VALUES ('huk', 'Hk123456', TRUE);
INSERT INTO authorities (username, authority) VALUES ('huk', 'ROLE_ADMIN');
INSERT INTO users (username, password, enabled) VALUES ('huk3', 'Hk123456', TRUE);
INSERT INTO authorities (username, authority) VALUES ('huk3', 'ROLE_USER');