use uc;

create table hengrun_user_profile
(
    user_id      varchar(36)   not null
        comment '主键id',
    full_name    varchar(10)   not null
        comment '姓名',
    nick_name    varchar(100)  not null
        comment '昵称',
    berth        varchar(100)  not null
        comment '货位',
    scope        varchar(1000) not null
        comment '经营范围',
    company_name varchar(100)  not null
        comment '公司名称',
    avatar       varchar(1000) not null
        comment '头像URL',
    primary key (user_id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '恒润用户档案表';
