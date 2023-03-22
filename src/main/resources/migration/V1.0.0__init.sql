CREATE TABLE `im_wx_admin`
(
    `id`         int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `wcId`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员微信号',
    `uuid`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员uuid【唯一】',
    `user_name`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员账号',
    `password`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
    `token`      varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'token',
    `created_at` datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by` int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at` datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by` int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted` tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_uuid` (`uuid`) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='管理员表';



CREATE TABLE `im_wx_send_msg_record`
(
    `id`         int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `wId`        varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送方微信实例ID',
    `fromuser`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送方微信号，对应user表的WcId',
    `touser`     varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '接收方微信号',
    `type`       int(4)                                           DEFAULT '0' COMMENT '消息发送类型，默认为0发送文本消息：0-文本，1-文件，2-图片，3-视频，4-语音，5-链接，6-名片，7-Emoji，8-小程序',
    `content`    varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '消息发送内容',
    `origin`     int(4)                                           DEFAULT '0' COMMENT '消息发送来源，默认0自动发送 0-自动发送 1-手动发送 2-欢迎语',
    `created_at` datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by` int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at` datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by` int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted` tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_fto` (`fromuser`, `touser`, `origin`) USING BTREE,
    KEY `idx_origin` (`origin`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='消息发送记录表';



CREATE TABLE `im_wx_user`
(
    `id`         int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `wcId`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信号【唯一】',
    `wId`        varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信实例ID',
    `nickName`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
    `headUrl`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '头像url',
    `wAccount`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机上显示的微信号',
    `sex`        tinyint(2)                                       DEFAULT '0' COMMENT '性别',
    `status`     int(4)                                           DEFAULT '3' COMMENT '状态:0已失效、1等待扫码、2扫码待确定、3扫码登录成功',
    `type`       int(4)                                           DEFAULT '3' COMMENT '是否权限最高：1、默认-2、是',
    `robert`     tinyint(2)                                       DEFAULT '0' COMMENT '是否开启自动回复，默认为0 0-不开启，1-开启机器人回复，2-开启自定义回复',
    `created_at` datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by` int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at` datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by` int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted` tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_wcId` (`wcId`) USING BTREE COMMENT '微信用户号唯一索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='微信用户表';



CREATE TABLE `im_wx_user_chatroom`
(
    `id`                 int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `user_wcId`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户的微信号',
    `chat_room_id`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群号',
    `nick_name`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群名称',
    `chat_room_owner`    varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群主微信号',
    `big_head_img_url`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '大头像',
    `small_head_img_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小头像',
    `v1`                 varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群v1',
    `member_count`       int(15)                                 NOT NULL DEFAULT '0' COMMENT '群成员数',
    `created_at`         datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by`         int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at`         datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by`         int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted`         tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_wcid` (`user_wcId`, `chat_room_id`) USING BTREE,
    KEY `idx_name_wcid` (`user_wcId`, `nick_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='微信用户群信息表';



CREATE TABLE `im_wx_user_chatroom_member_info`
(
    `id`                 int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `user_wcId`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户的微信号',
    `chat_room_id`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群号',
    `userName`           varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '成员微信号',
    `nickName`           varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '成员名称',
    `remark`             varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
    `signature`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '个性签名',
    `sex`                tinyint(2)                                       DEFAULT '0' COMMENT '性别0-女 1-男',
    `aliasName`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信号【用户自己设置的真实微信号】',
    `labelList`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签列表',
    `chat_room_owner`    varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '群主微信号',
    `big_head_img_url`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '大头像',
    `small_head_img_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小头像',
    `created_at`         datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by`         int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at`         datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by`         int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted`         tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_wcid_rooid` (`user_wcId`, `chat_room_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='微信用户群成员信息表';


CREATE TABLE `im_wx_user_friends`
(
    `id`         int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `user_wcId`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户的微信号',
    `wcId`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '好友的微信号wcId',
    `userName`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信号',
    `nickName`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
    `aliasName`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '微信号【用户自己设置的真实微信号】',
    `remark`     varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
    `signature`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '签名',
    `sex`        tinyint(2)                                       DEFAULT '0' COMMENT '性别',
    `bigHead`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '大头像',
    `labelList`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签列表',
    `robert`     tinyint(2)                                       DEFAULT '0' COMMENT '是否需要支持机器人自动聊天，默认为0-不需要 1-需要',
    `v1`         varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '好友的的wxId，都是以v1开头的一串数值',
    `created_at` datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by` int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at` datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by` int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted` tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_wcid` (`user_wcId`, `wcId`) USING BTREE COMMENT '好友唯一索引',
    KEY `idx_user_wcid` (`user_wcId`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='微信用户好友表';


CREATE TABLE `im_wx_user_label`
(
    `id`         int(11) unsigned                        NOT NULL AUTO_INCREMENT,
    `user_wcId`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户的微信号',
    `labelId`    int(15)                                          DEFAULT NULL COMMENT '标签id',
    `labelName`  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签名',
    `userName`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签内好友wcid',
    `nickName`   varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标签内好友昵称',
    `created_at` datetime                                         DEFAULT NULL COMMENT '创建时间',
    `created_by` int(10)                                          DEFAULT NULL COMMENT '创建人编码',
    `updated_at` datetime                                         DEFAULT NULL COMMENT '更新时间',
    `updated_by` int(10)                                          DEFAULT NULL COMMENT '最后更新人ID',
    `is_deleted` tinyint(2)                                       DEFAULT '0' COMMENT '是否删除（0：未删除 1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_user_lable_name` (`user_wcId`, `labelName`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='微信用户标签表';






















