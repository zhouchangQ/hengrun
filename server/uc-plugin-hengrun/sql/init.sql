use uc;

create table hengrun_user_profile
(
    user_id      varchar(36)   not null
        comment '主键id',
    full_name    varchar(10)   null
        comment '姓名',
    nick_name    varchar(100)  null
        comment '昵称',
    berth        varchar(100)  null
        comment '货位',
    scope        varchar(1000) null
        comment '经营范围',
    company_name varchar(100)  null
        comment '公司名称',
    avatar       varchar(1000) null
        comment '头像URL',
    primary key (user_id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '恒润用户档案表';

# 插入恒润采购员权限组
INSERT INTO sys_group (id, code, description, name)
VALUES (uuid(), 'g_purchaser', '恒润采购员组', 'Purchasers');
# 插入恒润采购员权限
INSERT INTO sys_authority (authority, description, name)
VALUES ('PURCHASER', '恒润采购员权限', 'Purchaser');
# 插入恒润采购员权限组与恒润采购员权限的关系映射
INSERT INTO sys_group_authority (group_id, authority)
VALUES ((SELECT id
         FROM sys_group
         WHERE code = 'g_purchaser'), 'PURCHASER');
