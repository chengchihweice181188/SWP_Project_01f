<%-- 
    Document   : updateProfile
    Created on : Oct 14, 2024, 4:10:54 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./stylesheet/updateProfile.css">
        <title>Cập nhật thông tin tài khoản</title>       
    </head>
    <body>
        <div class="account-details-container">
            <div class="logo-container">
                <img src="./WebLogo/z5885423386947_01ed9c1cee6274a97fc8fac2d98c4808-removebg-preview.png" alt="Fast Food Logo">
            </div>
            <div class="account-info">
                <h2>Cập nhật thông tin tài khoản</h2>
                <c:if test="${not empty messageE}">
                    <div class="error-message" style="color:red;">
                        ${messageE}
                    </div>
                </c:if>
                <!-- Hiển thị thông báo lỗi nếu có -->

                <!-- Form gửi dữ liệu -->
                <form action="UpdateProfile" method="post">
                    <!-- Tên người dùng -->
                    <div class="input-container">
                        <label for="username">Tên người dùng</label>
                        <input type="text" name="username" id="username" 
                               value="<%= session.getAttribute("Users") != null ? ((User) session.getAttribute("Users")).getUsername() != null ? ((User) session.getAttribute("Users")).getUsername() : "Không có tên" : ""%>" 
                               readonly>
                    </div>
                    <!-- Email -->
                    <div class="input-container">
                        <label for="email">Email</label>
                        <c:if test="${not empty messageEmail}">
                            <div class="error-message" style="color:red;">
                                ${messageEmail}
                            </div>
                        </c:if>
                        <input type="email" name="email" id="email" 
                               value="<%= session.getAttribute("Users") != null ? ((User) session.getAttribute("Users")).getEmail() != null ? ((User) session.getAttribute("Users")).getEmail() : "Không có email" : ""%>" 
                               placeholder="Nhập địa chỉ email" required>
                    </div>
                    <!-- Số điện thoại -->
                    <div class="input-container">
                        <label for="phone">Số điện thoại</label>
                        <c:if test="${not empty messagePhone}">
                            <div class="error-message" style="color:red;">
                                ${messagePhone}
                            </div>
                        </c:if>
                        <input type="text" name="phone" id="phone" 
                               value="<%= session.getAttribute("Users") != null ? ((User) session.getAttribute("Users")).getPhone_number() != null ? ((User) session.getAttribute("Users")).getPhone_number() : "Không có số điện thoại" : ""%>" 
                               placeholder="Nhập số điện thoại" required>
                    </div>
                    <!-- Địa chỉ -->
                    <div class="input-container">
                        <label for="address">Địa chỉ</label>
                        <input type="text" name="address" id="address" 
                               value="<%= session.getAttribute("Users") != null ? ((User) session.getAttribute("Users")).getAddress() != null ? ((User) session.getAttribute("Users")).getAddress() : "Không có địa chỉ" : ""%>" 
                               placeholder="Nhập địa chỉ" required>
                    </div>
                    <!-- Nút cập nhật thông tin -->
                    <div class="buttons">
                        <button type="submit">Cập nhật thông tin</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
