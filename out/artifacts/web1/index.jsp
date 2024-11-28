<%@ page import="java.util.Map" %>
<%@ page import="org.web1.dao.UserSessionRedis" %>
<%@ page import="org.web1.entity.UserLogin" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <style>
    /* 基本样式 */
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f4;
      display: flex;
      justify-content: flex-start; /* 从顶部开始排列 */
      align-items: center;
      height: 100vh;
      flex-direction: column; /* 修改这里，使容器内的元素竖直排列 */
    }

    .container {
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      max-width: 800px;
      width: 100%;
      box-sizing: border-box;
      margin: 10px 0; /* 添加一些间距，使容器之间有间隔 */
    }

    h1 {
      font-size: 24px;
      margin-bottom: 20px;
      text-align: center;
    }

    a {
      display: inline-block;
      margin: 10px;
      padding: 10px 20px;
      background-color: #007BFF;
      color: #fff;
      text-decoration: none;
      border-radius: 4px;
      transition: background-color 0.3s ease;
    }

    a:hover {
      background-color: #0056b3;
    }

    #cookieInfo {
      margin: 20px 0;
      padding: 10px;
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 4px;
      word-wrap: break-word; /* 允许长单词或 URL 地址换行到下一行 */
      overflow-wrap: break-word; /* 同上 */
      max-height: 400px; /* 设置最大高度 */
      overflow-y: auto; /* 超出部分显示滚动条 */
    }

    ul {
      list-style-type: none;
      padding: 0;
    }

    ul li {
      padding: 10px;
      border-bottom: 1px solid #ddd;
    }

    ul li:last-child {
      border-bottom: none;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    th, td {
      padding: 10px;
      text-align: left;
      border: 1px solid #ddd;
      word-wrap: break-word; /* 允许长单词或 URL 地址换行到下一行 */
      overflow-wrap: break-word; /* 同上 */
    }

    th {
      background-color: #f2f2f2;
      font-weight: bold;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    form {
      margin-top: 20px;
      text-align: center;
    }

    input[type="submit"] {
      padding: 10px 20px;
      background-color: #007BFF;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
      background-color: #0056b3;
    }
    .links {
      display: flex;
      justify-content: space-around;
      width: 100%; /* 占满整个容器宽度 */
    }
    .logout-form {
      margin-top: 10px; /* 调整间距 */
    }
    /* 响应式设计 */
    @media (max-width: 768px) {
      .container {
        padding: 15px;
      }

      h1 {
        font-size: 20px;
      }

      a {
        margin: 5px;
        padding: 8px 15px;
      }

      table {
        font-size: 14px;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h1><%= "Hello World!This is web1,port:9000" %></h1>
  <br/>
  <div class="links">
    <a href="http://127.0.0.1:9002/index">To web2</a>
    <a href="http://127.0.0.1:9003/home">To Home</a>
  </div>
  <div class="logout-form">
    <form action="logout" method="get">
      <input type="submit" value="Logout">
    </form>
  </div>
</div>
<div class="container">
  <h1>Current Cookies</h1>
  <div id="cookieInfo"></div>
  <script src="scripts/displayCookies.js"></script>
</div>
<div class="container">
  <%
    UserSessionRedis sessionManager = new UserSessionRedis();
    Map<String, String> users = sessionManager.getAllUsers();
    int activeSessionCount = sessionManager.getCurrentActiveSessions();
    if (users.isEmpty()) {
      out.println("<p>No users are currently logged in.</p>");
    } else {
      out.println("<h2>Current Active Sessions: " + activeSessionCount + "</h2>");
      out.println("<ul>");
      for (Map.Entry<String, String> entry : users.entrySet()) {
        String userId = entry.getKey();
        long loginTime = Long.parseLong(entry.getValue());
        out.println("<li>User ID: " + userId + ", Login Time: " + new java.util.Date(loginTime) + "</li>");
      }
      out.println("</ul>");
    }
  %>
  <h1>All Users Login History</h1>
  <table border="1">
    <tr>
      <th>UserId</th>
      <th>LoginTime</th>
    </tr>
    <%
      List<UserLogin> loginHistory = (List<UserLogin>) request.getAttribute("loginHistory");
      if (loginHistory != null && !loginHistory.isEmpty()) {
        for (UserLogin login : loginHistory) {
    %>
    <tr>
      <td><%= login.getUserId() %></td>
      <td><%= login.getLoginTime() %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="2">No Records Found</td>
    </tr>
    <%
      }
    %>
  </table>
</div>
</body>
</html>