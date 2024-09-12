DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(200) NOT NULL COMMENT '用户名',
    `password`    varchar(200) NOT NULL COMMENT '用户密码',
    `user_type`   tinyint(4) DEFAULT NULL COMMENT '用户类型，1：管理端',
    `create_time` datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time` datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
);

INSERT INTO `t_user` (`id`, `username`, `password`, `user_type`, `create_time`, `update_time`)
VALUES (1, 'eric', '{bcrypt}$2a$10$3sEoRaZtxvO20/bPlqLbAej71aGPplRqbCXoa1lcjFae0aSmyrVne', 1, '2024-09-11 18:59:48',
        '2024-09-11 18:59:48');