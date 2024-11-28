<%--
  Created by IntelliJ IDEA.
  User: 26861
  Date: 11/27/2024
  Time: 11:08 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
</head>
<body>
<div class="container">
    <h1>Register Page</h1>
    <form action="register" method="post" onsubmit="return validateForm()">
        <label for="regUserId">UserId:</label>
        <input type="text" id="regUserId" name="regUserId" required><br><br>
        <label for="regPassword">Password:</label>
        <input type="password" id="regPassword" name="regPassword" required><br><br>
        <label for="regConfirmPassword">Confirm Password:</label>
        <input type="password" id="regConfirmPassword" name="regConfirmPassword" required><br><br>
        <span id="passwordError" class="error-message"></span>
        <input type="submit" value="Register">
    </form>
</div>
<script>
    function validateForm() {
        const password = document.getElementById('regPassword').value;
        const confirmPassword = document.getElementById('regConfirmPassword').value;
        const passwordError = document.getElementById('passwordError');

        if (password !== confirmPassword) {
            passwordError.textContent = 'Passwords do not match';
            return false;
        } else {
            passwordError.textContent = '';
            return true;
        }
    }
</script>
</body>
</html>
