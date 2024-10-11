<%-- 
    Document   : promotionCreate
    Created on : Oct 10, 2024, 3:00:00 PM
    Author     : Bang
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Khuyến Mãi</title>
        <style>
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                justify-content: center;
                align-items: center;
                z-index: 9999;
            }
            .popup {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
                max-width: 400px;
                width: 90%;
            }
            .form-group {
                margin-bottom: 10px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
            }
            .voucher-fields {
                display: none;
            }
            .promotion-fields {
                display: none;
            }
        </style>
        <script>
            function openCreatePromotionPopup() {
                document.getElementById('createPromotionOverlay').style.display = 'flex';
                toggleFields(); // Hiển thị đúng các trường khi popup mở
            }

            function closeCreatePromotionPopup() {
                document.getElementById('createPromotionOverlay').style.display = 'none';
            }

            // Hàm để hiển thị các trường dựa trên loại khuyến mãi/voucher
            function toggleFields() {
                const promotionType = document.getElementById('promotionType').value;
                const promotionFields = document.querySelector('.promotion-fields');
                const voucherFields = document.querySelector('.voucher-fields');

                if (promotionType === 'promotion') {
                    promotionFields.style.display = 'block';
                    voucherFields.style.display = 'none';
                    // Kích hoạt các trường khuyến mãi
                    promotionFields.querySelectorAll('input').forEach(input => input.disabled = false);
                    // Vô hiệu hóa các trường voucher
                    voucherFields.querySelectorAll('input').forEach(input => input.disabled = true);
                } else if (promotionType === 'voucher') {
                    promotionFields.style.display = 'none';
                    voucherFields.style.display = 'block';
                    // Vô hiệu hóa các trường khuyến mãi
                    promotionFields.querySelectorAll('input').forEach(input => input.disabled = true);
                    // Kích hoạt các trường voucher
                    voucherFields.querySelectorAll('input').forEach(input => input.disabled = false);
                }
            }
            function validateDiscount(input) {
                let value = parseInt(input.value);
                if (value < 0 || value > 100) {
                    alert('Giảm giá phải nằm trong khoảng từ 0 đến 100%');
                    input.value = '';
                } else {
                    // Làm tròn số nguyên
                    input.value = Math.round(value);
                }
            }
            function validatePromotionDates() {
                const startDateInput = document.getElementById('promotionValidFrom');
                const endDateInput = document.getElementById('promotionValidTo');
                const startDate = new Date(startDateInput.value);
                const endDate = new Date(endDateInput.value);

                if (startDate && endDate && startDate > endDate) {
                    alert('Ngày bắt đầu không được sau ngày kết thúc!');
                    // Xóa giá trị không hợp lệ
                    startDateInput.value = '';
                    endDateInput.value = '';
                }
            }

            function validateVoucherDates() {
                const startDateInput = document.getElementById('voucherValidFrom');
                const endDateInput = document.getElementById('voucherValidTo');
                const startDate = new Date(startDateInput.value);
                const endDate = new Date(endDateInput.value);

                if (startDate && endDate && startDate > endDate) {
                    alert('Ngày bắt đầu không được sau ngày kết thúc!');
                    // Xóa giá trị không hợp lệ
                    startDateInput.value = '';
                    endDateInput.value = '';
                }
            }

        </script>
    </head>
    <body>
        <sql:setDataSource var="dataSource"
                           driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                           url="jdbc:sqlserver://ASUS3DK:1433;databaseName=swp_pj_04f;encrypt=true;trustServerCertificate=true"
                           user="sa"
                           password="sa123" />

        <sql:query dataSource="${dataSource}" var="productList">
            SELECT product_id, product_name FROM Products WHERE is_hidden = 0
        </sql:query>

        <div id="createPromotionOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeCreatePromotionPopup()">&#10006;</span>
                <h2>Thêm Khuyến Mãi</h2>
                <form action="${pageContext.request.contextPath}/AddPromotionServlet" method="post">

                    <div class="form-group">
                        <label for="promotionType">Loại:</label>
                        <select id="promotionType" name="promotionType" onchange="toggleFields()" required>
                            <option value="promotion">Khuyến mãi</option>
                            <option value="voucher">Voucher</option>
                        </select>
                    </div>

                    <!-- Trường cho Khuyến mãi -->
                    <div class="promotion-fields">
                        <div class="form-group">
                            <label for="promotionDiscount">Giảm giá (%):</label>
                            <input type="number" id="promotionDiscount" name="promotionDiscount" placeholder="Nhập % giảm giá" required min="0" max="100" step="1" oninput="validateDiscount(this)">
                        </div>
                        <div class="form-group">
                            <label for="promotionValidFrom">Ngày bắt đầu:</label>
                            <input type="datetime-local" id="promotionValidFrom" name="promotionValidFrom" required onchange="validatePromotionDates()">
                        </div>
                        <div class="form-group">
                            <label for="promotionValidTo">Ngày kết thúc:</label>
                            <input type="datetime-local" id="promotionValidTo" name="promotionValidTo" required onchange="validatePromotionDates()">
                        </div>
                    </div>

                    <!-- Trường cho Voucher -->
                    <div class="voucher-fields">
                        <div class="form-group">
                            <label for="voucherCode">Mã Voucher:</label>
                            <input type="text" id="voucherCode" name="voucherCode" placeholder="Nhập mã voucher" required>
                        </div>
                        <div class="form-group">
                            <label for="voucherDiscount">Giảm giá (%):</label>
                            <input type="number" id="voucherDiscount" name="voucherDiscount" placeholder="Nhập % giảm giá" required min="0" max="100" step="1" oninput="validateDiscount(this)">
                        </div>
                        <div class="form-group">
                            <label for="voucherValidFrom">Ngày bắt đầu:</label>
                            <input type="datetime-local" id="voucherValidFrom" name="voucherValidFrom" required onchange="validateVoucherDates()"> 
                        </div>
                        <div class="form-group">
                            <label for="voucherValidTo">Ngày kết thúc:</label>
                            <input type="datetime-local" id="voucherValidTo" name="voucherValidTo" required onchange="validateVoucherDates()">
                        </div>
                    </div>

                    <!-- Danh sách sản phẩm -->
                    <div class="form-group">
                        <label for="productId">Sản phẩm:</label>
                        <select id="productId" name="productId" required>
                            <c:forEach var="product" items="${productList.rows}">
                                <option value="${product.product_id}">${product.product_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit">Thêm</button>
                    <button type="button" onclick="closeCreatePromotionPopup()">Hủy</button>
                </form>
            </div>
        </div>
    </body>
</html>
