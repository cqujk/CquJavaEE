<%--
  Created by IntelliJ IDEA.
  User: 26861
  Date: 11/13/2024
  Time: 11:40 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.JwtUtil" %>
<%
    String username = null;
    Cookie[] cookies= request.getCookies();
    if(cookies!=null){
        for(Cookie cookie: cookies){
            if("sso-token".equals(cookie.getName())){
                username = JwtUtil.getClaimsFromToken(cookie.getValue()).getSubject();
                break;
            }
        }
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Welcome, <%= username %>!</h2>
    <a href="/logout">Logout</a>
<%--    <a href="logout">Logout</a>--%>
</body>
</html>
