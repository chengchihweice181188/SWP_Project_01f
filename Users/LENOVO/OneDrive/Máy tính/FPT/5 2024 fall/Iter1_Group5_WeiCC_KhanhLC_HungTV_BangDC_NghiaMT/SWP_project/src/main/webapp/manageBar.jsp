<%-- 
    Document   : manageBar
    Created on : Oct 6, 2024, 3:38:05 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="./assets/stylesheet/manageBar.css"/>
        <style>
            .sidebar {
                width: 250px;
                background-color: #FF9F1C; /* Màu cam */
                height: 100vh;
                position: fixed;
                left: 0;
                top: 0;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                align-items: center;
                padding: 20px 0;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1); /* Bóng đổ nhẹ */
            }

            /* Logo container */
            .logo-container {
                width: 100%;
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }

            .logo {
                max-width: 120px;
                height: auto;
            }

            /* Navigation links */
            .nav-links {
                display: flex;
                flex-direction: column;
                align-items: flex-start; /* Căn các mục sang trái */
                width: 100%;
                padding-left: 20px; /* Tạo khoảng cách từ trái */
            }

            .nav-links a {
                text-decoration: none;
                font-size: 16px;
                color: #333;
                font-weight: bold;
                padding: 10px 0;
                width: 100%;  /* Đảm bảo liên kết chiếm toàn bộ chiều rộng */
                text-align: left;  /* Căn trái các liên kết */
                margin: 5px 0; /* Khoảng cách giữa các liên kết */
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .nav-links a:hover {
                background-color: #FF6F1C;
                color: white;
            }

            /* Nút logout */
            .logout-btn {
                background-color: #FF5C5C; /* Màu đỏ nhạt cho nút */
                color: white;
                font-weight: bold;
                border: none;
                padding: 10px 20px;
                border-radius: 5px; /* Bo tròn các góc */
                cursor: pointer;
                margin-top: 20px;
                text-align: left;
                transition: background-color 0.3s ease; /* Hiệu ứng khi hover */
            }

            .logout-btn:hover {
                background-color: #FF1C1C; /* Thay đổi màu khi hover */
            }

            .logout-btn:active {
                background-color: #D91C1C; /* Màu khi nhấn */
                transform: scale(0.98); /* Nhỏ lại một chút khi nhấn */
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <div class="logo-container">
                <img src="/WebLogo/logo.jpg" alt="Logo" class="logo">
            </div>
            <div class="nav-links">
                <a href="/OrderManagement" class="text">Quản lí đơn hàng</a>
                <a href="#" class="text">Quản lý danh mục</a>
                <a href="/ManageProduct" class="text">Quản lý sản phẩm</a>
                <a href="#" class="text">Quản lý lựa chọn</a>
                <a href="#" class="text">Quản lý khuyến mãi</a>
                <a href="#" class="text">Thống kê doanh thu</a>
                <a href="#" class="text">Thống kê sản phẩm</a>
                <a href="#" class="text">Xem danh sách đánh giá</a>
                <a href="#" class="text">Quản lí tài khoản</a>
            </div>
            <form action="Logout" method="post" onsubmit="confirmLogout(event)">
                <div class="auth-buttons">
                    <button class = "logout-btn">Đăng xuất</button>
                </div>
            </form>
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