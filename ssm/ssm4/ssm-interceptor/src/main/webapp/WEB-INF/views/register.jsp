<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/6
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>
<form id="registerform" name="registerform" action="/user/register" method="POST">
    <input type="text" id="userName" name="userName" />
    <input type="text" id="password" name="password" />
    <input type="submit" id="submit" name="register" />
</form>
<a href="login">reback to login</a>
<script type="text/javascript" language="javascript">
    if('${status}' !=''){
        if(1 == '${status}'){
            alert('failed to register for the same user!');
        }
        else{
            alert('succeed to register!');
        }
    }
</script>
</body>
</html>
