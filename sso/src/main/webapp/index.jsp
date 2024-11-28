<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
<div class="container">

    <h1>Login in! This is SSO port:9003</h1>
    <br/>
    <form action="login" method="post">
        <label for="userId">UserId:</label>
        <input type="text" id="userId" name="userId" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <div class="form-buttons">
            <input type="submit" value="Login">
            <a href="register.jsp" class="button">Register</a>
            <button type="button" class="button" onclick="openRecoverModal()">Recover</button>
        </div>
    </form>
    <!-- 恢复用户模态窗口 -->
    <div id="recoverModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeRecoverModal()">&times;</span>
            <h2>Recover User</h2>
            <form action="recoverUser" method="post">
                <label for="userId">User ID:</label>
                <input type="text" id="userId" name="userId" required>
                <br>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <br>
                <input type="submit" value="Recover">
            </form>
        </div>
    </div>
    <%--    <div class="sidebar-trigger"></div>--%>
    <%--    <div class="sidebar">--%>
    <%--        <h2>Current Cookies</h2>--%>
    <%--        <div id="cookieInfo"></div>--%>
    <%--    </div>--%>
</div>
<%--<script src="scripts/displayCookies.js"></script>--%>
<script src="scripts/sidebar.js"></script>
</body>
</html>
<!-- 模态窗口样式 -->
<style>
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
    }
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 30%;
    }
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }
    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    .form-buttons {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 10px;
    }
    .form-buttons .button {
        padding: 10px 20px;
        background-color: #007BFF;
        color: white;
        border: none;
        cursor: pointer;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        border-radius: 5px;
    }
</style>
<!-- 模态窗口脚本 -->
<script>
    function openRecoverModal() {
        document.getElementById('recoverModal').style.display = 'block';
    }

    function closeRecoverModal() {
        document.getElementById('recoverModal').style.display = 'none';
    }
</script>