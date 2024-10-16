<%-- 
    Document   : changePassword.jsp
    Created on : Oct 16, 2024, 10:28:43 AM
    Author     : Luu Chi Khanh-CE181175
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập nhật mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="./stylesheet/changePassword.css">
        <!-- Tự động chuyển trang về trang Profile.jsp sau 5 giây nếu có thông báo thành công -->
    </head>
    <body>
        <div class="password-update-container">
            <div class="logo-container">
                <img src="./WebLogo/z5885423386947_01ed9c1cee6274a97fc8fac2d98c4808-removebg-preview.png" alt="Fast Food Logo">
            </div>
            <div class="account-info">
                <h2>Cập nhật mật khẩu</h2>

                <!-- Hiển thị thông báo lỗi -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
                    </div>
                </c:if>

                <!-- Hiển thị thông báo thành công với Bootstrap -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                        <%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : ""%>
                    </div>
                </c:if>

                <form action="ChangePassword" method="post" onsubmit="return confirmChangePassword()">
                    <div class="input-container">
                        <label for="oldPassword">Nhập mật khẩu cũ</label>
                        <input type="password" id="oldPassword" name="oldPassword" placeholder="Nhập mật khẩu cũ" required>
                    </div>

                    <div class="input-container">
                        <label for="newPassword">Nhập mật khẩu mới</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>
                    </div>

                    <div class="input-container">
                        <label for="confirmPassword">Nhập lại mật khẩu mới</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>
                    </div>

                    <div class="buttons">
                        <button type="submit">Cập nhật mật khẩu</button>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${not empty successMessage}">
            <script>
                setTimeout(function(){
                    window.location.href = 'index.jsp';
                }, 5000); // Chuyển sau 5 giây
            </script>
        </c:if>
        <!-- Thêm Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Script xác nhận trước khi thay đổi mật khẩu -->
        <script>
                    function confirmChangePassword() {
                        return confirm("Bạn có chắc chắn muốn thay đổi mật khẩu không?");
                    }
        </script>
    </body>
</html>

