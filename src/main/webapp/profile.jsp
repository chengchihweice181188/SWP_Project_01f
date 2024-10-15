<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết tài khoản</title>
        <link rel="stylesheet" href="./stylesheet/profile.css">
    </head>
    <body>
        <div class="container">
            <div class="account-info">
                <img src="./WebLogo/z5885423386947_01ed9c1cee6274a97fc8fac2d98c4808-removebg-preview.png" alt="Food Fast Food" class="logo">
                <h1>Chi tiết tài khoản</h1>
                <p><strong>Tên người dùng: </strong><span><%= session.getAttribute("Users") != null && ((User) session.getAttribute("Users")).getUsername() != null ? ((User) session.getAttribute("Users")).getUsername() : "Không có tên người dùng"%></span></p>
                <!-- Email -->
                <p><strong>Email: </strong><span><%= session.getAttribute("Users") != null && ((User) session.getAttribute("Users")).getEmail() != null ? ((User) session.getAttribute("Users")).getEmail() : "Không có email"%></span></p>
                <!-- Số điện thoại -->
                <p><strong>Số điện thoại: </strong><span><%= session.getAttribute("Users") != null && ((User) session.getAttribute("Users")).getPhone_number() != null ? ((User) session.getAttribute("Users")).getPhone_number() : "Không có số điện thoại"%></span></p>
                <!-- Địa chỉ -->
                <p><strong>Địa chỉ: </strong><span><%= session.getAttribute("Users") != null && ((User) session.getAttribute("Users")).getAddress() != null ? ((User) session.getAttribute("Users")).getAddress() : "Không có địa chỉ"%></span></p>
                <div class="button-group">
                    <button type="button" onclick="location.href = 'updateProfile.jsp'">Cập nhật thông tin</button>
                    <button type="button" onclick="location.href = 'changePassword.jsp'">Đổi mật khẩu</button>
                    <form action="Logout" method="post" onsubmit="confirmLogout(event)">
                        <div class="auth-buttons">
                            <button class = "logout-btn">Đăng xuất</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <script>
            function confirmLogout(event) {
                const confirmation = confirm("Bạn có chắc chắn muốn đăng xuất không?");
                if (!confirmation) {
                    event.preventDefault(); // Ngăn chặn form nếu người dùng chọn "Cancel"
                }
            }
        </script>
    </body>
</html>
