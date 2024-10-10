<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./stylesheet/profile.css"/>
    </head>
    <body>
        <div class="profile-container">
            <img src="WebLogo/z5885423386947_01ed9c1cee6274a97fc8fac2d98c4808-removebg-preview.png" alt="Fast Food Logo">
            <h2>Chi tiết tài khoản</h2>
            <div class="profile-info">
                <p><span>Tên người dùng:</span> Customer1</p>
                <p><span>Email:</span> Cus1@gmail.com</p>
                <p><span>Số điện thoại:</span> 012345678</p>
                <p><span>Địa chỉ:</span> 399z Long An</p>
            </div>
            <div class="button-container">
                <form action="Profile" method="POST">
                    <!-- Nút cập nhật thông tin -->
                    <button type="submit" name="action" value="updateProfile">Cập nhật thông tin</button>

                    <!-- Nút đổi mật khẩu -->
                    <button type="submit" name="action" value="changePassword">Đổi mật khẩu</button>

                    <!-- Nút đăng xuất -->
                    <button type="submit" name="action" value="logout">Đăng xuất</button>
                </form>
            </div>
        </div>
    </body>
</html>
