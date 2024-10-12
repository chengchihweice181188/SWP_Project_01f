<%-- 
    Document   : register
    Created on : Oct 7, 2024, 5:20:32 PM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký</title>
        <link rel="stylesheet" href="./assets/css/register.css">
        <script src="./assets/js/register.js"></script>
    </head>
    <body>
        <div class="register-container">
             <div class="logo">
                <img src="WebLogo/logo.jpg" alt="Fast Food Logo">
             </div>
            <h2>Đăng ký</h2>
            <form action="Register" method="POST" name="registerForm" onsubmit="return validateForm()">
                <input type="text" placeholder="Tên người dùng" name="username" required>
                <input type="password" placeholder="Mật khẩu" name="password" required>
                <input type="email" placeholder="Email" name="email" required>
                <input type="tel" placeholder="Số điện thoại" name="phone" required>
                <input type="text" placeholder="Địa chỉ" name="address" required>
                <button type="submit" class="login-btn">Đăng ký</button>
                <p>Đã có tài khoản? <a href="index.jsp" class="register-link">Đăng nhập</a></p>
                <c:if test="${not empty msg}">
                    <div class="error">${msg}</div>
                </c:if>
            </form>
        </div>
    </body>
</html>