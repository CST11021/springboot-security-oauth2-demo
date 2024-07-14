-- 客户端接入配置表
drop table if exists `oauth_client_details`;
create table `oauth_client_details` (
  `client_id` varchar(256) not null comment '客户端ID',
  `resource_ids` varchar(256) default null comment '客户端所能访问的资源id集合',
  `client_secret` varchar(256) default null comment '客户端秘钥',
  `scope` varchar(256) default null comment '客户端申请的权限范围,可选值包括read,write,trust',
  `authorized_grant_types` varchar(256) default null comment '客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials',
  `web_server_redirect_uri` varchar(256) default null comment '客户端的重定向URI',
  `authorities` varchar(256) default null comment '客户端所拥有的Spring Security的权限值',
  `access_token_validity` int(11) default null comment '客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)',
  `refresh_token_validity` int(11) default null comment '客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)',
  `additional_information` varchar(4096) default null comment '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,在实际应用中, 可以用该字段来存储关于客户端的一些其他信息',
  `autoapprove` varchar(256) default null comment '用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为’true’或支持的scope值,则会跳过用户Approve的页面,直接授权',
  primary key (`client_id`)
) engine=innodb default charset=utf8 comment '客户端配置表';
-- 初始化数据
BEGIN;
    insert into `oauth_client_details` values ('oauth2', 'oauth2,resourceOne', '$2a$10$Cf.ui70XRGI.xUDM24xuLOCH0n/Xz7s6hsL1U0DSjpets913KTym.', 'all', 'authorization_code,password,implicit,client_credentials,refresh_token', 'http://example.com', '', 7200, null, null, '');
    insert into `oauth_client_details` values ('clientOne', '', '$2a$10$CP09LP7yMA6E0kvjeYTSue7MaftiVeAJHH4ZwfLshrJD9fux1mWAO', 'all', 'authorization_code,refresh_token', 'http://localhost:8081/clientOne/login', null, 60, 60, null, 'true');
    insert into `oauth_client_details` values ('clientTwo', '', '$2a$10$sXDQlRTo6RHNZVUS0XXBTe2kX9RBTCJesb49JzadFXziOAJ1GnkXO', 'all', 'authorization_code,refresh_token', 'http://localhost:8082/clientTwo/login', '', 60, 60, '{"site":"henan"}', 'false');
    insert into `oauth_client_details` values ('UserManagement', null, '$2a$10$ZRmPFVgE6o2aoaK6hv49pOt5BZIKBDLywCaFkuAs6zYmRkpKHgyuO', 'all', 'authorization_code,refresh_token', 'http://localhost:8083/memberSystem/login', null, 7200, null, null, 'true');
    insert into `oauth_client_details` values ('OrderManagement', null, '$2a$10$8yVwRGY6zB8wv5o0kRgD0ep/HVcvtSZUZsYu/586Egxc1hv3cI9Q6', 'all', 'authorization_code,refresh_token', 'http://localhost:8084/orderSystem/login', null, 7200, null, null, 'true');
COMMIT;


-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(64) NOT NULL COMMENT '账号',
    `password` varchar(256) NOT NULL COMMENT '密码',
    `nickname` varchar(64) NOT NULL COMMENT '昵称',
    `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0：锁定，1：解锁）',
    `create_user` varchar(64) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_user` varchar(64) DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 comment '用户表';
-- 初始化数据
BEGIN;
    INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '管理员', 'abc@123.com', 1, 'system', '2019-02-12 11:12:19', NULL, NULL);
    INSERT INTO `sys_user` VALUES (2, 'zhangsan', '123456', '张三', 'zhangsan@126.com', 1, 'system', '2019-02-12 11:13:27', NULL, NULL);
COMMIT;


-- 权限表
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `pid` int(11) DEFAULT '0' COMMENT '父ID',
    `type` tinyint(4) NOT NULL COMMENT '资源类型（1：菜单，2：按钮，3：操作）',
    `name` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '资源名称',
    `code` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '资源标识（或者叫权限字符串）',
    `uri` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '资源URI',
    `seq` int(11) DEFAULT '1' COMMENT '序号',
    `create_user` varchar(64) CHARACTER SET latin1 DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_user` varchar(64) CHARACTER SET latin1 DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`),
    KEY `idx_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment '权限表';
-- 初始化数据
BEGIN;
    INSERT INTO `sys_permission` VALUES (1, 0, 3, 'add', 'member:detail', '/member/detail', 1, 'system', '2019-03-03 18:50:17', 'system', '2019-03-03 18:50:20');
    INSERT INTO `sys_permission` VALUES (2, 0, 3, 'add', 'member:add', '/member/add', 1, 'system', '2019-03-03 18:50:17', 'system', '2019-03-03 18:50:20');
COMMIT;


-- 角色表
drop table if exists `sys_role`;
create table `sys_role` (
    `id` int(11) not null auto_increment,
    `role_name` varchar(32) not null comment '角色名称',
    `role_code` varchar(32) not null,
    `role_description` varchar(64) default null comment '角色描述',
    `create_user` varchar(64) default null,
    `create_time` datetime default current_timestamp,
    `update_user` varchar(64) default null,
    `update_time` datetime default null,
    primary key (`id`)
) engine=innodb auto_increment=4 default charset=utf8mb4 comment '角色表';
-- 初始化数据
begin;
    insert into `sys_role` values (1, '管理员', 'manager', '管理员', 'system', '2019-02-12 11:15:37', null, null);
    insert into `sys_role` values (2, '员工', 'normal', '普通员工', 'system', '2019-02-12 11:14:41', null, null);
commit;


-- 角色和权限关系表
drop table if exists `sys_role_permission`;
create table `sys_role_permission` (
    `id` int(11) not null auto_increment,
    `role_id` int(11) not null comment '角色id',
    `permission_id` int(11) not null comment '权限id',
    primary key (`id`)
) engine=innodb auto_increment=2 default charset=utf8mb4 comment '角色和权限关系表';
-- 初始化数据
begin;
    insert into `sys_role_permission` values (1, 1, 1);
    insert into `sys_role_permission` values (2, 1, 2);
    insert into `sys_role_permission` values (3, 2, 1);
commit;


-- 用户角色表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL COMMENT '用户ID',
    `role_id` int(11) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
-- 初始化数据
BEGIN;
    INSERT INTO `sys_user_role` VALUES (1, 1, 1);
    INSERT INTO `sys_user_role` VALUES (2, 2, 2);
COMMIT;



-- 如果是使用JDBC存储session需要用到以下两个表
drop table if exists spring_session_attributes;
drop table if exists spring_session;
create table spring_session (
                                primary_id char(36) not null,
                                session_id char(36) not null,
                                creation_time bigint not null,
                                last_access_time bigint not null,
                                max_inactive_interval int not null,
                                expiry_time bigint not null,
                                principal_name varchar(100),
                                constraint spring_session_pk primary key (primary_id)
) engine=innodb row_format=dynamic;
create unique index spring_session_ix1 on spring_session (session_id);
create index spring_session_ix2 on spring_session (expiry_time);
create index spring_session_ix3 on spring_session (principal_name);

create table spring_session_attributes (
                                           session_primary_id char(36) not null,
                                           attribute_name varchar(200) not null,
                                           attribute_bytes blob not null,
                                           constraint spring_session_attributes_pk primary key (session_primary_id, attribute_name),
                                           constraint spring_session_attributes_fk foreign key (session_primary_id) references spring_session(primary_id) on delete cascade
) engine=innodb row_format=dynamic;