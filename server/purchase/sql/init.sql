create database purchase
    default character set utf8mb4
    default collate utf8mb4_bin;

use purchase;

create table purchase
(
    id         bigint unsigned not null auto_increment
        comment '主键id',
    title      varchar(50)     not null
        comment '标题',
    tel        varchar(20)     not null
        comment '联系方式',
    type       varchar(10)     not null
        comment '产品类型',
    details    varchar(1000)   not null
        comment '详情',
    publish_at datetime(3)     not null
        comment '发布时刻',
    publish_by varchar(255)    not null
        comment '发布用户id',
    completed  bool            not null
        comment '采购结束',
    create_at  datetime(3)     not null
        comment '数据插入时间',
    modify_at  datetime(3)     not null
        comment '最后修改时间',
    deleted    bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '采购信息表';

create table purchase_album
(
    id          bigint unsigned not null auto_increment
        comment '主键id',
    purchase_id bigint unsigned not null
        comment '采购信息id',
    image_id    varchar(36)     not null
        comment '图片URL',
    order_index int unsigned    not null
        comment '顺序',
    create_at   datetime(3)     not null
        comment '数据插入时间',
    modify_at   datetime(3)     not null
        comment '最后修改时间',
    deleted     bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '采购信息相册表';

create table quote
(
    id          bigint unsigned not null auto_increment
        comment '主键id',
    purchase_id bigint unsigned not null
        comment '采购信息id',
    tel         varchar(20)     not null
        comment '联系方式',
    details     varchar(1000)   not null
        comment '详情',
    quote_at    datetime(3)     not null
        comment '报价时刻',
    quote_by    varchar(255)    not null
        comment '报价用户id',
    create_at   datetime(3)     not null
        comment '数据插入时间',
    modify_at   datetime(3)     not null
        comment '最后修改时间',
    deleted     bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '报价表';

create table quote_album
(
    id          bigint unsigned not null auto_increment
        comment '主键id',
    quote_id    bigint unsigned not null
        comment '报价信息id',
    image_id    varchar(36)     not null
        comment '图片URL',
    order_index int unsigned    not null
        comment '顺序',
    create_at   datetime(3)     not null
        comment '数据插入时间',
    modify_at   datetime(3)     not null
        comment '最后修改时间',
    deleted     bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '报价相册表';

create table sale
(
    id         bigint unsigned not null auto_increment
        comment '主键id',
    title      varchar(50)     not null
        comment '标题',
    tel        varchar(20)     not null
        comment '联系方式',
    type       varchar(10)     not null
        comment '产品类型',
    details    varchar(1000)   not null
        comment '详情',
    publish_at datetime(3)     not null
        comment '发布时刻',
    publish_by varchar(255)    not null
        comment '发布用户id',
    create_at  datetime(3)     not null
        comment '数据插入时间',
    modify_at  datetime(3)     not null
        comment '最后修改时间',
    deleted    bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '甩卖信息表';

create table sale_album
(
    id          bigint unsigned not null auto_increment
        comment '主键id',
    sale_id     bigint unsigned not null
        comment '甩卖信息id',
    image_id    varchar(36)     not null
        comment '图片URL',
    order_index int unsigned    not null
        comment '顺序',
    create_at   datetime(3)     not null
        comment '数据插入时间',
    modify_at   datetime(3)     not null
        comment '最后修改时间',
    deleted     bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '甩卖信息相册表';

create table headline
(
    id         bigint unsigned not null auto_increment
        comment '主键id',
    title      varchar(50)     not null
        comment '标题',
    details    varchar(1000)   not null
        comment '详情',
    publish_at datetime(3)     not null
        comment '发布时刻',
    publish_by varchar(255)    not null
        comment '发布用户id',
    create_at  datetime(3)     not null
        comment '数据插入时间',
    modify_at  datetime(3)     not null
        comment '最后修改时间',
    active     bool            not null
        comment '是否开启',
    deleted    bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '首页弹窗';

create table headline_album
(
    id          bigint unsigned not null auto_increment
        comment '主键id',
    headline_id bigint unsigned not null
        comment '甩卖信息id',
    image_id    varchar(36)     not null
        comment '图片URL',
    order_index int unsigned    not null
        comment '顺序',
    create_at   datetime(3)     not null
        comment '数据插入时间',
    modify_at   datetime(3)     not null
        comment '最后修改时间',
    deleted     bool            not null
        comment '删除标记',
    primary key (id)
)
    engine = InnoDB
    default character set utf8mb4
    default collate utf8mb4_bin
    comment = '首页弹窗相册表';
