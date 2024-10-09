<%-- 
    Document   : forgetPassword
    Created on : Oct 7, 2024, 5:34:44 PM
    Author     : Luu Chi Khanh-CE181175
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quên mật khẩu</title>
        <link rel="stylesheet" href="./stylesheet/forgetPassword.css">
    </head>
    <body>
        <div class="forget-password-container">
            <h2>Quên mật khẩu</h2>
            <p>Nhập địa chỉ email của bạn để nhận mã OTP đăng nhập tạm thời:</p>
            <form action="ForgetPassword" method="post">
                <input type="email" name="email" placeholder="Nhập địa chỉ email của bạn" required>
                <button type="submit" class="send-otp-btn">Gửi mã OTP</button>
            </form>
        </div>
    </body>
</html>
