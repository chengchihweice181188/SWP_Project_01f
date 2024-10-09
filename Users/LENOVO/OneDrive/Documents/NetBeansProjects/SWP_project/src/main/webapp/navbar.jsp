<%-- 
    Document   : navbar
    Created on : Oct 6, 2024, 2:18:12 PM
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
            .editNavbar{
                border: solid;
                border-width: 2px;
                background: orange;
                position: fixed;
                width: 100%;
            }
            .logo {
                position: absolute;
                width: 70px;
                height: 68px;
                margin-top: -34px;
            }
            .item-txt{
                font-size: 24px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light editNavbar">
            <div class="container-fluid">
                <a class="navbar-brand" href="index.jsp">
                    <img src="/WebLogo/logo.jpg" alt="Logo" class="logo">
                </a>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link item-txt" href="#">Đơn hàng</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link item-txt" href="#">| Giỏ hàng</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link item-txt" href="#">| Thông báo</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link item-txt" href="#">| Tài khoản</a>
                    </li>
                </ul>
            </div>
        </nav>
    </body>
</html>
