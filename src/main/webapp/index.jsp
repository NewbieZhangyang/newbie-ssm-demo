<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试多数据源模式</title>
</head>
<body>
<h2> 通过设置：DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);的方式，来动态设置数据源</h2>
<a href="searchAllUser?dataSourceKey=master">查询主库：用户信息</a><br/><br/>
<a href="searchAllUser?dataSourceKey=slave1">查询从库1：用户信息</a><br/><br/>
<a href="searchAllUser?dataSourceKey=slave2">查询从库2：用户信息</a><br/><br/>
<br/><hr/><hr/><br/>
<h2> 使用自定义注解的方式，来动态设置数据源</h2>
<a href="searchMaster">查询主库：用户信息</a><br/><br/>
<a href="searchSlave1">查询从库1：用户信息</a><br/><br/>
<a href="searchSlave2">查询从库2：用户信息</a><br/><br/>

</body>
</html>
