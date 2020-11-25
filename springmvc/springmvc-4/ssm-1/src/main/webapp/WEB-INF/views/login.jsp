<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/6
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>

<form id="loginform" name="loginform" action="/user/login" method="POST">
    <input type="text" id="userName" name="userName" />
    <input type="text" id="password" name="password" />
    <input type="submit" id="submit" name="submit" />
</form>

<a href="register"> goto register </a>
</body>


<script type="text/javascript" language="javascript">
        if('${status}' !=''){
            if(1 == '${status}'){
                alert('failed to login for no this user!');
            }else if(2 == '${status}'){
                alert('failed to login for error password!');
            }
            else{
                alert('succeed to login!123');
                location.href = '/user/userInfo'
            }
        }
</script>

</html>
