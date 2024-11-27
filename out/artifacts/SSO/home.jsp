<%--
  Created by IntelliJ IDEA.
  User: 26861
  Date: 11/27/2024
  Time: 2:41 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!This is Home port:9003" %>
</h1>
<br/>
<a href="http://127.0.0.1:9000/index">To web1</a>
<a href="http://127.0.0.1:9002/index">To web2</a>
<h1>Current Cookies</h1>
<div id="cookieInfo"></div>
<script src="scripts/displayCookies.js"></script>
</body>
</html>
