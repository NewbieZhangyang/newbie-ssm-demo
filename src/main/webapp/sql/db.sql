# 用户信息表
DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id VARCHAR(25) not null comment '用户ID',
	username VARCHAR(50) not null comment '用户名',
	title VARCHAR(25) comment '职级',
  remark varchar(50) default '主库master' comment '信息备注',
  primary key(id)
);

# 用户银行账户表
drop table if exists user_account;
create table user_account(
  account_id varchar(25) not null comment '账户ID',
  user_id varchar(25) not null comment '用户ID',
  balance int  default 0 comment '账户余额',
  remark varchar(50) default '主库master' comment '信息备注',
  primary key (account_id)
);

# 用户股票记录表
drop table if exists user_stock;
create table user_stock(
  stock_id varchar (25) not null comment '股票ID',
  user_id varchar (25) not null comment '用户ID',
  stock_name varchar (32) comment '股票名称',
  count_num int default 0 comment '拥有股的数量',
  remark varchar(50) default '主库master' comment '信息备注',
  primary key (stock_id)
);

#批量修改ramark字段的默认值
alter table user alter column remark drop default;
alter table user_account alter column remark drop default;
alter table user_stock alter column remark drop default;
alter table user alter COLUMN remark set DEFAULT '从库-salve-04';
alter table user_account alter COLUMN remark set DEFAULT '从库-salve-04';
alter table user_stock alter COLUMN remark set DEFAULT '从库-salve-04';

#插入测试数据
insert into user(id,username,title) values('newbie','简小六','程序猿');
insert into user_account (account_id,user_id,balance) values ('6222-0001','newbie',100);
insert into user_stock (stock_id,user_id,stock_name,count_num) values ('AB-01','newbie','AB股','0');