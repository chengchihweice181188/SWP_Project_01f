<%-- 
    Document   : promotion
    Created on : Oct 10, 2024, 12:02:19 PM
    Author     : Bang
--%>

<%@page import="Model.Voucher"%>
<%@page import="java.util.List"%>
<%@page import="Model.Promotion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Khuyến mãi</title>

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #fff9f0;
            }

            .sidebar {
                width: 250px;
                background-color: #f4a261;
                height: 100vh;
                position: fixed;
                left: 0;
                top: 0;
                padding: 20px;
                box-sizing: border-box;
            }

            .sidebar ul {
                list-style-type: disc;
                padding-left: 20px;
                margin-top: 20px;
            }

            .sidebar ul li {
                margin-bottom: 15px;
            }

            .sidebar ul li a {
                text-decoration: none;
                color: #333;
                font-size: 18px;
                display: block;
            }

            .sidebar ul li a:hover {
                color: #264653;
            }

            .btn-danger {
                background-color: #e76f51;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                width: 100%;
                text-align: center;
                margin-bottom: 20px;
                border-radius: 5px;
            }

            .main-content {
                margin-left: 250px;
                padding: 20px;
                flex-grow: 1;
            }

            .promo-item {
                display: flex;
                align-items: center;
                padding: 10px;
                border-bottom: 1px solid #ccc;
            }

            .promo-item img {
                width: 60px;
                height: 60px;
                margin-right: 10px;
            }

            .promo-item div {
                flex: 1;
                padding: 0 10px;
            }

            .actions {
                display: flex;
                gap: 10px;
            }

            .actions button {
                padding: 5px 10px;
                border: none;
                border-radius: 5px;
            }

            .edit-btn {
                background-color: #007bff;
                color: white;
            }

            .delete-btn {
                background-color: #dc3545;
                color: white;
            }

            .search-bar {
                display: flex;
                justify-content: flex-end;
                width: 300px;
            }

            .search-bar input {
                padding: 8px;
                width: 100%;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }

            .header {
                text-align: center;
                margin-bottom: 20px;
            }

            .header h2 {
                font-size: 36px;
                font-weight: bold;
                color: #333;
                margin: 0;
            }

            .header-actions {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 10px;
                gap: 20px;
            }

            .btn-add {
                padding: 8px 16px;
                background-color: #6c757d;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
            }

            .alert {
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
            }

            .alert-success {
                background-color: #d4edda;
                color: #155724;
            }

            .alert-error {
                background-color: #f8d7da;
                color: #721c24;
            }

            .promo-list {
                margin-top: 20px;
            }


        </style>
        <script>
            // Các hàm JavaScript để mở và đóng các popup
            function openCreatePromotionPopup() {
                document.getElementById('createPromotionOverlay').style.display = 'flex';
            }

            function closeCreatePromotionPopup() {
                document.getElementById('createPromotionOverlay').style.display = 'none';
            }

            function openEditPromotionPopup(id, discount, validFrom, validTo, productId) {
                document.getElementById('editPromotionOverlay').style.display = 'flex';
                document.getElementById('editPromotionId').value = id;
                document.getElementById('editPromotionDiscount').value = discount;
                document.getElementById('editPromotionValidFrom').value = validFrom;
                document.getElementById('editPromotionValidTo').value = validTo;
                document.getElementById('editProductId').value = productId;
            }

            function closeEditPromotionPopup() {
                document.getElementById('editPromotionOverlay').style.display = 'none';
            }

            function openDeletePromotionPopup(id) {
                document.getElementById('deletePromotionOverlay').style.display = 'flex';
                document.getElementById('deletePromotionId').value = id;
            }

            function closeDeletePromotionPopup() {
                document.getElementById('deletePromotionOverlay').style.display = 'none';
            }
            // Hàm mở popup chỉnh sửa voucher
            function openEditVoucherPopup(id, code, discount, validFrom, validTo) {
                console.log('Received data:', {id, code, discount, validFrom, validTo}); // Debugging
                document.getElementById('editVoucherOverlay').style.display = 'flex';
                document.getElementById('editVoucherId').value = id;
                document.getElementById('editVoucherCode').value = code;
                document.getElementById('editVoucherDiscount').value = discount;
                document.getElementById('editVoucherValidFrom').value = validFrom;
                document.getElementById('editVoucherValidTo').value = validTo;
            }
            function openDeleteVoucherPopup(id) {
                document.getElementById('deleteVoucherOverlay').style.display = 'flex';
                document.getElementById('deleteVoucherId').value = id;
            }

        </script>
    </head>
    <body>

        <div class="container">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="logo">
                    <img src="food_logo.png" alt="Food Logo">
                </div>
                <ul class="menu-list">
                    <li><a href="${pageContext.request.contextPath}/OrderManagement">Quản lí đơn hàng</a></li>
                    <li><a href="${pageContext.request.contextPath}/Category">Quản lý danh mục</a></li>
                    <li><a href="${pageContext.request.contextPath}/Product">Quản lý sản phẩm</a></li>
                    <li><a href="${pageContext.request.contextPath}/Option">Quản lý lựa chọn</a></li>
                    <li><a href="${pageContext.request.contextPath}/Promotion">Quản lý khuyến mãi</a></li>
                    <li><a href="${pageContext.request.contextPath}/Revenue">Thống kê doanh thu</a></li>
                    <li><a href="${pageContext.request.contextPath}/BestSeller">Thống kê sản phẩm</a></li>
                    <li><a href="${pageContext.request.contextPath}/Reviews">Xem danh sách đánh giá</a></li>
                    <li><a href="${pageContext.request.contextPath}/Account">Quản lí tài khoản</a></li>
                </ul>
                <button class="btn btn-danger">Logout</button>
            </div>


            <div class="main-content">
                <%-- Display success or error messages --%>
                <%
                    String successMessage = (String) request.getSession().getAttribute("successMessage");
                    String errorMessage = (String) request.getSession().getAttribute("errorMessage");

                    if (successMessage != null) {
                %>
                <div class="alert alert-success"><%= successMessage%></div>
                <%
                        request.getSession().removeAttribute("successMessage");
                    }

                    if (errorMessage != null) {
                %>
                <div class="alert alert-error"><%= errorMessage%></div>
                <%
                        request.getSession().removeAttribute("errorMessage");
                    }
                %>
                <div class="header">
                    <h2>Quản lý khuyến mãi</h2>
                </div>
                <div class="header-actions">
                    <form class="search-bar" method="GET" action="${pageContext.request.contextPath}/Promotion">
                        <input type="text" name="search" placeholder="Tìm kiếm khuyến mãi...">
                        <button type="submit" class="btn-add">Tìm kiếm</button>
                    </form>
                    <button onclick="openCreatePromotionPopup()" class="btn-add">Thêm khuyến mãi</button>
                </div>




                <div class="promo-list">
                    <h3>Danh sách Promotions</h3>
                    <div class="promo-section">
                        <%
                            List<Promotion> promotions = (List<Promotion>) request.getAttribute("promotions");
                            if (promotions != null && !promotions.isEmpty()) {
                                for (Promotion promotion : promotions) {
                        %>
                        <div class="promo-item">
                            <img src="https://via.placeholder.com/60" alt="Promotion">
                            <div>Sản phẩm: <%= promotion.getProductName()%></div>
                            <div>Giảm giá: <%= promotion.getPromotionDiscount()%>%</div>
                            <div>Thời gian: <%= promotion.getPromotionValidFrom()%> - <%= promotion.getPromotionValidTo()%></div>
                            <div class="actions">
                                <%
                                    String validFromFormatted = "";
                                    String validToFormatted = "";
                                    if (promotion.getPromotionValidFrom() != null) {
                                        validFromFormatted = promotion.getPromotionValidFrom().format(dateTimeFormatter);
                                    }
                                    if (promotion.getPromotionValidTo() != null) {
                                        validToFormatted = promotion.getPromotionValidTo().format(dateTimeFormatter);
                                    }
                                %>
                                <button onclick="openEditPromotionPopup(
                                                '<%= promotion.getPromotionID()%>',
                                                '<%= promotion.getPromotionDiscount()%>',
                                                '<%= promotion.getPromotionValidFrom() != null ? promotion.getPromotionValidFrom().format(dateTimeFormatter) : ""%>',
                                                '<%= promotion.getPromotionValidTo() != null ? promotion.getPromotionValidTo().format(dateTimeFormatter) : ""%>',
                                                '<%= promotion.getProductId()%>'
                                                )">Chỉnh sửa</button>


                                <button class="delete-btn" onclick="openDeletePromotionPopup(<%= promotion.getPromotionID()%>)">Xóa</button>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p>Không có khuyến mãi nào.</p>
                        <% } %>
                    </div>

                    <h3>Danh sách Vouchers</h3>
                    <div class="promo-section">
                        <%
                            // Lấy danh sách các voucher từ request attribute
                            List<Voucher> vouchers = (List<Voucher>) request.getAttribute("vouchers");

                            // Kiểm tra nếu danh sách voucher không rỗng
                            if (vouchers != null && !vouchers.isEmpty()) {
                                for (Voucher voucher : vouchers) {
                                    // Định dạng ngày giờ cho validFrom và validTo của voucher
                                    String validFromFormatted = "";
                                    String validToFormatted = "";
                                    if (voucher.getVoucherValidFrom() != null) {
                                        validFromFormatted = voucher.getVoucherValidFrom().format(dateTimeFormatter);
                                    }
                                    if (voucher.getVoucherValidTo() != null) {
                                        validToFormatted = voucher.getVoucherValidTo().format(dateTimeFormatter);
                                    }

                        %>
                        <div class="promo-item">
                            <img src="https://via.placeholder.com/60" alt="Voucher">
                            <div>Mã voucher: <%= voucher.getVoucherCode()%></div>
                            <div>Giảm giá: <%= voucher.getVoucherDiscount()%>%</div>
                            <div>Thời gian: <%= validFromFormatted%> - <%= validToFormatted%></div>
                            <div class="actions">
                                <button class="edit-btn" 
                                        onclick="openEditVoucherPopup(
                                                        '<%= voucher.getVoucherID()%>',
                                                        '<%= voucher.getVoucherCode()%>',
                                                        '<%= voucher.getVoucherDiscount()%>',
                                                        '<%= validFromFormatted%>',
                                                        '<%= validToFormatted%>'
                                                        )">
                                    Chỉnh sửa
                                </button>

                                <button class="delete-btn" 
                                        onclick="openDeleteVoucherPopup('<%= voucher.getVoucherID()%>')">
                                    Xóa
                                </button>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p>Không có voucher nào.</p>
                        <%
                            }
                        %>
                    </div>


                    <!-- Nhúng các popup vào trang -->
                    <jsp:include page="voucherDelete.jsp"></jsp:include>
                    <jsp:include page="voucherEdit.jsp"></jsp:include>
                    <jsp:include page="promotionCreate.jsp"></jsp:include>
                    <jsp:include page="promotionEdit.jsp"></jsp:include>
                    <jsp:include page="promotionDelete.jsp"></jsp:include>
                    </body>
                    </html>
