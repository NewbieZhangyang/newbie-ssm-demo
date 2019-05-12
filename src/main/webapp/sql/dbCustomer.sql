# 人员信息表
DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
  id varchar(25) not null comment '主键id',
	idcard_no VARCHAR(18) not null comment '身份证号',
	name VARCHAR(50)  comment '姓名',
	sex CHAR(2) comment '性别',
  remark varchar(50) default '主库-master-01' comment '信息备注',
  primary key(id)
);

#插入测试数据
-- insert into customer(id,idcard_no,name,sex) values('123','410222199100001111','简小六','男');
-- update customer set name ='你会很幸福的' ;
-- delete from customer;
select * from customer;

################################ 增加同步触发器
create TRIGGER tr_insert_customer
after INSERT on customer for EACH ROW
BEGIN
	-- 向从库nb_slave_01中的customer表，插入一条数据
	insert into nb_slave_01.customer
	(id,idcard_no,name,sex)
	values (new.id,new.idcard_no,new.name,new.sex);
END;

#use nb_master;
create TRIGGER tr_update_customer
after UPDATE on customer for EACH ROW
BEGIN
	-- 向从库中同步数据
	update nb_slave_01.customer
	set id=new.id,idcard_no=new.idcard_no,name=new.name,sex=new.sex
	where id=new.id;
END;

#use nb_master;
create TRIGGER tr_delete_customer
before DELETE on customer for EACH ROW
BEGIN
	-- 向从库中同步数据
	delete from nb_slave_01.customer
	where id=old.id;
END;