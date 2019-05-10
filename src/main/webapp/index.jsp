<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试多数据源模式</title>
</head>
<body>
<h2>练习一：访问多数据源</h2>
<h3> 方法中显示设置 DynamicDataSourceHolder.setDataSourceKey(dataSourceKey)的方式</h3>
<a href="searchAllUser?dataSourceKey=master">查询主库：用户信息</a><br/><br/>
<a href="searchAllUser?dataSourceKey=slave1">查询从库1：用户信息</a><br/><br/>
<a href="searchAllUser?dataSourceKey=slave2">查询从库2：用户信息</a><br/><br/>
<a href="searchAllUser?dataSourceKey=slave3">查询从库3：用户信息</a><br/><br/>
<h3> 使用自定义注解的方式，来动态设置数据源</h3>
<a href="searchMaster">查询主库：用户信息</a><br/><br/>
<a href="searchSlave1">查询从库1：用户信息</a><br/><br/>
<a href="searchSlave2">查询从库2：用户信息</a><br/><br/>
<br/><hr/><hr><hr/><br/>

<h2>练习二：单数据库中事务操作</h2>
<h3>方式一：使用注解方式：@</h3>
<a href="notTransactionCommit">没有增加事务控制</a><br/><br/>
<a href="annotationCommit">事务控制：使用注解的方式</a><br/><br/>
<a href="aopAdviceCommit"> 事务控制：基于AOP切面的方式</a><br/><br/>
<a href="resetData"> 重置数据：账户余额=100 和 股票票数=0</a><br/><br/>
<br/><hr/><hr><hr/><br/>

<h2>练习三：实现主从读写分离</h2>
<a href="insertOneUser">插入主库：向主库增加一个用户</a><br/><br/>
<a href="queryAllUser">查询从库：使用query方法，查询从库数据</a><br/><br/>
<a href="notQueryAllUser">查询主库（非正常操作）：使用非query方法，查询主库数据</a><br/><br/>
</body>
</html>
