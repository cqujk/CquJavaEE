<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!This is web1,port:9000" %></h1>
<br/>
<a href="http://127.0.0.1:9002/index">To web2</a>
<a href="http://127.0.0.1:9003/home">To Home</a>
</body>
<h1>Current Cookies</h1>
<div id="cookieInfo"></div>
<script src="scripts/displayCookies.js"></script>
</html>