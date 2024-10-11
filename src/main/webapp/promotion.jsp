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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/promotion.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
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
        </script>
    </head>
    <body>

        <div class="container">
            <div class="sidebar">
                <h2>Food Fast Food</h2>
                <ul class="menu-list">
                    <li><a href="#">Quản lý danh mục</a></li>
                    <li><a href="#">Quản lý sản phẩm</a></li>
                    <li><a href="#">Quản lý lựa chọn</a></li>
                    <li><a href="/Promotion" class="active">Quản lý khuyến mãi</a></li>
                    <li><a href="#">Thống kê doanh thu</a></li>
                    <li><a href="#">Thống kê sản phẩm</a></li>
                    <li><a href="#">Xem danh sách đánh giá</a></li>
                </ul>
            </div>

            <div class="main-content">
                <div class="header">
                    <h2>Quản lý khuyến mãi</h2>
                    <button onclick="openCreatePromotionPopup()" class="btn-add">Thêm khuyến mãi</button>
                    <div class="search-bar">
                        <input type="text" placeholder="Tìm kiếm khuyến mãi...">
                    </div>
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
                            List<Voucher> vouchers = (List<Voucher>) request.getAttribute("vouchers");
                            if (vouchers != null && !vouchers.isEmpty()) {
                                for (Voucher voucher : vouchers) {
                        %>
                        <div class="promo-item">
                            <img src="https://via.placeholder.com/60" alt="Voucher">
                            <div>Mã voucher: <%= voucher.getVoucherCode()%></div>
                            <div>Giảm giá: <%= voucher.getVoucherDiscount()%>%</div>
                            <div>Thời gian: <%= voucher.getVoucherValidFrom()%> - <%= voucher.getVoucherValidTo()%></div>
                            <div class="actions">
                                <button class="edit-btn" onclick="openEditPromotionPopup(<%= voucher.getVoucherID()%>, <%= voucher.getVoucherDiscount()%>, '<%= voucher.getVoucherValidFrom()%>', '<%= voucher.getVoucherValidTo()%>', null)">Sửa</button>
                                <button class="delete-btn" onclick="openDeletePromotionPopup(<%= voucher.getVoucherID()%>)">Xóa</button>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p>Không có voucher nào.</p>
                        <% }%>

                    </div>
                </div>
            </div>
        </div>

        <!-- Nhúng các popup vào trang -->
        <jsp:include page="promotionCreate.jsp"></jsp:include>
        <jsp:include page="promotionEdit.jsp"></jsp:include>
        <jsp:include page="promotionDelete.jsp"></jsp:include>
    </body>
</html>
