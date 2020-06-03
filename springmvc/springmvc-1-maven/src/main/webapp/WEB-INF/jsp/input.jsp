<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Spring MVC 传参方式</title>
</head>
<body>
<form action="/param" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>

<form action="/param1" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>

<form action="/param2" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>

<form action="/param3" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>
</body>
</html>