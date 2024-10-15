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
        <style>
            body {
                display: flex;
                margin: 0;
            }
            .sidebar {
                width: 210px;
                background-color: orange;
                top: 0;
                left: 0;
                padding: 10px;
                box-sizing: border-box;
            }
            .sidebar .logo {
                width: 100%;
                height: auto;
                margin-bottom: 20px;
            }
            .sidebar ul {
                list-style-type: none;
                padding: 0;
            }
            .sidebar ul li {
                margin: 10px 0;
            }
            .sidebar ul li a {
                text-decoration: none;
                color: white;
                font-size: 18px;
                display: block;
                padding: 10px;
                background-color: transparent;
                transition: background-color 0.3s;
            }
            .btn-danger {
                margin-top: 20px;
                width: 100%;
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <div>
                <img src="/WebLogo/logo.jpg" alt="Logo" class="logo">
            </div>
            <ul>
                <li><a href="#" class="text">Quản lí đơn hàng</a></li>
                <li><a href="#" class="text">Quản lý danh mục</a></li>
                <li><a href="#" class="text">Quản lý sản phẩm</a></li>
                <li><a href="#" class="text">Quản lý lựa chọn</a></li>
                <li><a href="#" class="text">Quản lý khuyến mãi</a></li>
                <li><a href="#" class="text">Thống kê doanh thu</a></li>
                <li><a href="#" class="text">Thống kê sản phẩm</a></li>
                <li><a href="#" class="text">Xem danh sách đánh giá</a></li>
                <li><a href="#" class="text">Quản lí tài khoản</a></li>
            </ul>
            <button class="btn btn-danger">Logout</button>
        </div>
    </body>
</html>