<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>Login in! This is sso port:9003</h1>
<br/>
<form action="login" method="post">
    <label for="userId">UserId:</label>
    <input type="text" id="userId" name="userId" required><br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>
    <input type="submit" value="Login">
</form>
<h1>Current Cookies</h1>
<div id="cookieInfo"></div>
<script src="scripts/displayCookies.js"></script>
</body>
</html>