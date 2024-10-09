<%-- 
    Document   : index
    Created on : Oct 6, 2024, 9:27:32 PM
    Author     : Luu Chi Khanh-CE181175
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>
        <link rel="stylesheet" href="./stylesheet/style.css">
    </head>
    <body>
        <div class="login-container">
            <div class="logo">
                <img src="assets/z5885423386947_01ed9c1cee6274a97fc8fac2d98c4808-removebg-preview.png" alt="Fast Food Logo">
            </div>
            <h2>Đăng nhập</h2>
            <form action="LoginServlet" method="post">
                <input type="text" placeholder="Tên người dùng" name="username" required>
                <input type="password" placeholder="Mật khẩu" name="password" required>
                <h3>${requestScope.msg}</h3>
                <div class="remember-me-container">
                    <input type="checkbox" id="remember-me" name="remember-me">
                    <label for="remember-me">Remember me</label>
                    <a onclick="window.location.href = 'forgetPassword.jsp'" class="forgot-password">Quên mật khẩu</a>
                </div>
                <button type="submit" class="login-btn">Đăng nhập</button>
                <button type="button" class="google-login-btn">Đăng nhập bằng Google</button>
                <p>Đã có tài khoản? <a href="#" class="register-link">Đăng ký</a></p>
            </form>
        </div>
    </body>
</html>
