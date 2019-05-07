-- DROP TABLE user;
CREATE TABLE user(
	id VARCHAR(25),
	username VARCHAR(50),
	title VARCHAR(255)
	);
insert into user(id,username,title) values('123','简小六','主库master');
insert into user(id,username,title) values('123','简小六','从库slave1');
insert into user(id,username,title) values('123','简小六','从库slave2');