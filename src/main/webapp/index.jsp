<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Hello World!</h1>
    <h2>Login</h2>
    <form action="/login" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Login">
    </form>
    <%
        String error = request.getParameter("error");
        if("true".equals(error)){
            out.print("<span style='color: red'>Invalid username or password</span>");
        }
    %>
</body>
</html>