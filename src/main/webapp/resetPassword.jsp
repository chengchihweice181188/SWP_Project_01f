<%-- 
    Document   : resetPassword
    Created on : Oct 18, 2024, 5:37:58 PM
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
        <link rel="stylesheet" href="./assets/stylesheet/resetPassword.css">
    </head>
    <body>
        <div class="container">
            <div class="logo">
                <img src="./WebLogo/logo.jpg" alt="Food Logo">
            </div>
            <h2>Đặt lại mật khẩu</h2>
            <!-- Hiển thị thông báo lỗi -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    <%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : ""%>
                </div>
            </c:if>
            <form action="ResetPassword" method="POST">
                <div class="input-group">
                    <label for="new-password">Nhập mật khẩu mới</label>
                    <input type="password" id="new-password" name="new-password" placeholder="Nhập mật khẩu mới của bạn" required>

                    <label for="confirm-password">Nhập lại mật khẩu mới</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="Nhập lại mật khẩu mới của bạn" required>
                </div>
                <button type="submit" class="btn">Xác nhận</button>
            </form>
        </div>
        <c:if test="${not empty successMessage}">
            <script>
                setTimeout(function () {
                    window.location.href = 'Login';
                }, 3000); // Chuyển sau 3 giây
            </script>
        </c:if>
    </body>
</html>

