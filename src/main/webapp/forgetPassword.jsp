<%-- 
    Document   : forgetPassword
    Created on : Oct 18, 2024, 2:34:05 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>Đặt lại mật khẩu</title>
        <link rel="stylesheet" href="./assets/stylesheet/forgetPassword.css">
    </head>
    <body>
        <div class="container">
            <div class="logo">
                <img src="./WebLogo/logo.jpg" alt="Food Logo">
            </div>
            <h2>Đặt lại mật khẩu</h2>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
                </div>
            </c:if>
            <form action="ForgetPassword" method="POST">
                <div class="input-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Nhập email của bạn" required>
                </div>
                <button type="submit" class="btn">Xác nhận</button>
            </form>
        </div>
    </body>
</html>
