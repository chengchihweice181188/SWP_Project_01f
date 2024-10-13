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
            .navbar{
                background: orange;
                width: 100%;
                display: flex;
                padding: 0px;
                position: fixed;
                border-bottom: solid;
                border-width: 2px;
            }
            .navbar-items{
                display: flex;
            }
            .logo{
                height: 88px;
                width: 88px;
            }
            .nav-icon{
                margin: 20px;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <div>
                <img src="/WebLogo/logo.jpg" alt="Logo" class="logo">
            </div>
            <div class="navbar-items">
                <a class="nav-link item-txt" href="#">
                    <img class="nav-icon" src="/WebLogo/order.png">
                </a>
                <a class="nav-link item-txt" href="#">
                    <img class="nav-icon" src="/WebLogo/cart.png">
                </a>
                <a class="nav-link item-txt" href="#">
                    <img class="nav-icon" src="/WebLogo/bell.png">
                </a>
                <a class="nav-link item-txt" href="#">
                    <img class="nav-icon" src="/WebLogo/user.png">
                </a>
            </div>
        </div>
    </body>
</html>
