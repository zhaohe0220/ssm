-- noinspection SqlNoDataSourceInspectionForFile
CREATE DATABASE seckill;
use seckill;
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000秒杀iPhone6',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('500秒杀iPad2',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('300秒杀小米4',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('799秒杀魅族5s',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');


  create table success_killed (
    `seckill_id` bigint not null comment '秒杀商品id',
    `user_phone` bigint not null comment '用户手机号',
    `state` tinyint not null comment '状态标示“-1无效 0：成功 1：已付款',
    `create_time` TIMESTAMP NOT NULL comment '创建时间',
    PRIMARY KEY (seckill_id,user_phone),
    KEY idx_create_time(create_time)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

