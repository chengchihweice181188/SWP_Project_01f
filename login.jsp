<%-- 
    Document   : login
    Created on : Oct 6, 2024, 9:42:04 PM
    Author     : Luu Chi Khanh-CE181175
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>HELLO Khanh</h1>
        <form action="signout" method="post">
            <div class="auth-buttons">
                <button class="login-button">Sign Out</button>
            </div>
            <a onclick="window.location.href = 'Profile.jsp'" class="update-profile">Xem thông tin</a>
        </form>
    </body>
</html>
