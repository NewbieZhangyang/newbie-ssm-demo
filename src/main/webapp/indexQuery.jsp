<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试：分库分表逻辑</title>
</head>
<body>
<h2>练习一：一主多从查询</h2>
<h3> 查询主库时，直接查询；查询从库时，求模后查询对应的从库</h3>
<a href="queryMaster">查询主库时，直接查询</a><br/><br/>
<a href="querySlave">查询从库时，求模后查询对应的从库</a><br/><br/>
<br/><hr/><hr><hr/><br/>

<h2>练习二：一致性hash算法分库方案</h2>
<h3> </h3>
<a href="addCustomer"> 插入十条数据，姓名为"简小六" </a><br/><br/>
<a href="queryCustomerFromMater">从master库中查询客户数据</a><br/><br/>
<a href="queryCustomer">从slave库中查询客户数据</a>
<br/><hr/><hr><hr/><br/>
</body>
</html>
