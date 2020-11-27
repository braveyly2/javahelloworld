<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <title>userInfo</title>
</head>
<body>
welcome ${user.name} !
<a href="sessionInfo">sessionInfo2</a>
<a href="logout"> logout2 </a>
<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown"  data-hover="dropdown" data-close-others="true">
        <span><spring:message code="language.choose" text="语言选择"/></span>
        <i class="icon-angle-down"></i>
    </a>
    <ul class="dropdown-menu">
        <li><a href="?language=zh_CN"> 中文</a></li>
        <li><a href="?language=en_US"> English</a></li>
    </ul>
    <spring:message code="navigation.home" text="首页"/>
</li>
</body>
</html>
