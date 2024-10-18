<%-- 
    Document   : VerifyEmailRSP
    Created on : Oct 18, 2024, 5:33:47 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>Xác nhận Email</title>
        <link rel="stylesheet" href="./assets/stylesheet/verifyEmail.css">
    </head>
    <body>
        <div class="container">
            <h2>Xác nhận Email</h2>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
                </div>
            </c:if>
            <form action="VerifyEmailRSP" method="POST">
                <input type="text" name="verificationCode" placeholder="Nhập mã xác thực" required>
                <button type="submit">Xác nhận</button>
            </form>
            <form action="resendCode" method="POST">
                <button type="submit">Gửi lại mã</button>
            </form>
        </div>
    </body>
</html>
