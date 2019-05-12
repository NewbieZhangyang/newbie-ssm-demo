#####################显示所有触发器
show TRIGGERS;
####################删除触发器
drop TRIGGER tr_insert_user;
drop TRIGGER tr_insert_userAccount;
drop TRIGGER tr_insert_userStock;
drop TRIGGER tr_update_user;
drop TRIGGER tr_update_userAccount;
drop TRIGGER tr_update_userStock;
drop TRIGGER tr_delete_user;
drop TRIGGER tr_delete_userAccount;
drop TRIGGER tr_delete_userStock;


##### 使用触发器，模拟主从数据库同步功能
##### 当主库执行insert/update/delete操作时，通过触发器，自动向从库同步修改的数据
################################ 为每张表增加插入数据同步触发器
# 插入数据同步触发器：用户表
#use nb_master;
create TRIGGER tr_insert_user
after INSERT on user for EACH ROW
BEGIN
	-- 向从库nb_slave_01中的user表，插入一条数据
	insert into nb_slave_01.user
	(id,username,title)
	values (new.id,new.username,new.title);
END;

# 插入数据同步触发器：用户账户表
#use nb_master;
create TRIGGER tr_insert_userAccount
after INSERT on user_account for EACH ROW
BEGIN
	-- 向从库nb_slave_01中的user表，插入一条数据
	insert into nb_slave_01.user_account
	(account_id,user_id,balance)
	values (new.account_id,new.user_id,new.balance);
END;

# 插入数据同步触发器：用户股票表
#use nb_master;
create TRIGGER tr_insert_userStock
after INSERT on user_stock for EACH ROW
BEGIN
	-- 向从库nb_slave_01中的user_stock表，插入一条数据
	insert into nb_slave_01.user_stock
	(stock_id,user_id,stock_name,count_num)
	values (new.stock_id,new.user_id,new.stock_name,new.count_num);
END;


################################ 为每张表增加插修改数据同步触发器
# 用户表
#use nb_master;
create TRIGGER tr_update_user
after UPDATE on user for EACH ROW
BEGIN
	-- 向从库中同步数据
	update nb_slave_01.user
	set username = new.username,title=new.title
	where id=new.id;
END;

# 用户账户表
#use nb_master;
create TRIGGER tr_update_userAccount
after UPDATE on user_account for EACH ROW
BEGIN
	-- 向从库中同步数据
	update nb_slave_01.user_account
	set user_id = new.user_id,balance=new.balance
	where account_id=new.account_id;
END;

# 用户股票表
#use nb_master;
create TRIGGER tr_update_userStock
after UPDATE on user_stock for EACH ROW
BEGIN
	-- 向从库中同步数据
	update nb_slave_01.user_stock
	set stock_name = new.stock_name,user_id=new.user_id,count_num=new.count_num
	where stock_id=new.stock_id;
END;

################################ 为每张表增加插删除数据同步触发器
# 用户表
#use nb_master;
create TRIGGER tr_delete_user
before DELETE on user for EACH ROW
BEGIN
	-- 向从库中同步数据
	delete from nb_slave_01.user
	where id=old.id;
END;

# 用户账户表
#use nb_master;
create TRIGGER tr_delete_userAccount
before DELETE on user_account for EACH ROW
BEGIN
	-- 向从库中同步数据
	delete from nb_slave_01.user_account
	where account_id=old.account_id;
END;

# 用户股票表
#use nb_master;
create TRIGGER tr_delete_userStock
before DELETE on user_stock for EACH ROW
BEGIN
	-- 向从库中同步数据
	delete from nb_slave_01.user_stock
	where stock_id=old.stock_id;
END;

