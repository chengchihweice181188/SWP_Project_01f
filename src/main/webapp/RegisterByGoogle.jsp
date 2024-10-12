<%-- 
    Document   : RegisterByGoogle
    Created on : Oct 11, 2024, 10:39:05 AM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Google Registration</title>
        <link rel="stylesheet" href="./assets/css/registerbygoogle.css">
        <script src="./assets/js/registerbygoogle.js"></script>
    </head>
    <body>
        <div class="register">
            <div class="logo">
                <img src="WebLogo/logo.jpg" alt="Fast Food Logo">
            </div>
            <h2>Đăng ký</h2>
            <div class="registers">
                <form action="RegisterByGoogle" method="POST"name="registerForm" onsubmit="return validateForm()">
                    <label for="username">Tên người dùng:</label>
                    <input type="text" id="username" name="username" placeholder="Tên người dùng" required>
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" value="${sessionScope.email}" readonly>
                    <label for="phone">Số điện thoại:</label>
                    <input type="text" id="phone" name="phone" placeholder="Số điện thoại" required>
                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" name="address" placeholder="Địa chỉ" required>
                    <button type="submit">Đăng ký</button>
                </form>
            </div>
        </div>
    </body>
</html>



