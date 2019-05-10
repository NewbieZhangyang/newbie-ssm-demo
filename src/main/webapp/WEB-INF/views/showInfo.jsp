<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示结果信息</title>
</head>
<body>
处理信息：${message}<br/>

<br/><br/><br/><br/>
处理结果：
&nbsp;&nbsp;&nbsp;&nbsp;用户ID：${user.id}
&nbsp;&nbsp;&nbsp;&nbsp;用户名：${user.username}
&nbsp;&nbsp;&nbsp;&nbsp;职 级：${user.title}
&nbsp;&nbsp;&nbsp;&nbsp;数据源：${user.remark}
</body>
</html>
