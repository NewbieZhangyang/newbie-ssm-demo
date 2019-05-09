<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示结果信息</title>
</head>
<body>
处理信息：${message}<br/>
处理结果：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;用户ID：${user.id} <br/>
&nbsp;&nbsp;&nbsp;&nbsp;用户名：${user.username}<br/>
&nbsp;&nbsp;&nbsp;&nbsp;职 级：${user.title}<br/>
&nbsp;&nbsp;&nbsp;&nbsp;数据源：${user.remark}<br/>
</body>
</html>
