#############数据同步触发器测试数据
#插入测试数据
insert into user(id,username,title) values('newbie','简小六','程序猿');
insert into user_account (account_id,user_id,balance) values ('6222-0001','newbie',100);
insert into user_stock (stock_id,user_id,stock_name,count_num) values ('AB-01','newbie','AB股','0');

# 修改数据
UPDATE user set username ='简小六，你会很幸福的' ;
UPDATE user_account set user_id ='简小六，你会很幸福的' ;
UPDATE user_stock set user_id ='简小六，你会很幸福的' ;

#清空数据
delete from user;
delete from user_account;
delete from user_stock;

#查询数据
select * from user;
select * from user_account;
select * from user_stock;
