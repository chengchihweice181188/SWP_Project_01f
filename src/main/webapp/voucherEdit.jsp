<%-- 
    Document   : voucherEdit.jsp
    Created on : Oct 12, 2024, 3:13:49 PM
    Author     : Bang
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<sql:setDataSource var="dataSource"
                   driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                   url="jdbc:sqlserver://ASUS3DK:1433;databaseName=swp_pj_04f;encrypt=true;trustServerCertificate=true"
                   user="sa"
                   password="sa123" />

<sql:query dataSource="${dataSource}" var="voucherList">
    SELECT voucher_id, voucher_code FROM Vouchers WHERE is_hidden = 0
</sql:query>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa Voucher</title>
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f9f9f9;
            }

            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: none;
                justify-content: center;
                align-items: center;
            }

            .popup {
                background: white;
                padding: 20px;
                border-radius: 8px;
                width: 400px;
                max-width: 100%;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                position: relative;
            }

            .popup h2 {
                margin-top: 0;
                text-align: center;
                font-size: 24px;
                color: #333;
            }

            .popup form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            .popup form label {
                font-weight: bold;
                font-size: 14px;
                color: #333;
                margin-bottom: 5px;
            }

            .popup form input[type="text"],
            .popup form input[type="number"],
            .popup form input[type="datetime-local"],
            .popup form select {
                width: 100%;
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                background-color: #f9f9f9;
            }

            .popup form button[type="submit"],
            .popup form button[type="button"] {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 48%;
            }

            .popup form .form-group-inline {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }

            .popup form button[type="submit"] {
                background-color: #4CAF50;
                color: white;
            }

            .popup form button[type="button"] {
                background-color: #f44336;
                color: white;
            }

            .popup form button[type="submit"]:hover,
            .popup form button[type="button"]:hover {
                opacity: 0.9;
            }

            .close-btn {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 20px;
                cursor: pointer;
                background: none;
                border: none;
            }
        </style>
        <script>
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

            // Hàm định dạng ngày giờ cho input datetime-local
            function formatDateTimeLocal(dateTimeString) {
                if (!dateTimeString)
                    return '';
                const date = new Date(dateTimeString);
                if (isNaN(date.getTime())) {
                    console.error('Invalid date:', dateTimeString);
                    return '';
                }
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
                const day = String(date.getDate()).padStart(2, '0');
                const hours = String(date.getHours()).padStart(2, '0');
                const minutes = String(date.getMinutes()).padStart(2, '0');

                return `${year}-${month}-${day}T${hours}:${minutes}`;
                    }

                    // Hàm đóng popup chỉnh sửa voucher
                    function closeEditVoucherPopup() {
                        document.getElementById('editVoucherOverlay').style.display = 'none';
                    }

                    // Hàm kiểm tra giá trị giảm giá
                    function validateDiscount(input) {
                        let value = parseInt(input.value);
                        if (value < 0 || value > 100 || isNaN(value)) {
                            alert('Giảm giá phải là số nguyên từ 0 đến 100%');
                            input.value = '';
                        } else {
                            // Làm tròn số nguyên
                            input.value = Math.round(value);
                        }
                    }

                    // Hàm kiểm tra ngày khuyến mãi hợp lệ
                    function validateVoucherDates() {
                        const startDateInput = document.getElementById('editVoucherValidFrom');
                        const endDateInput = document.getElementById('editVoucherValidTo');
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
        <div id="editVoucherOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeEditVoucherPopup()">&#10006;</span>
                <h2>Chỉnh sửa Voucher</h2>
                <form action="${pageContext.request.contextPath}/UpdatePromotionServlet" method="post">
                    <input type="hidden" name="voucherId" id="editVoucherId">
                    <!-- Thêm label cho từng input -->
                    <label for="editVoucherCode">Mã voucher</label>
                    <input type="text" name="voucherCode" id="editVoucherCode" placeholder="Mã voucher" required>
                    <label for="editVoucherDiscount">Phần trăm giảm giá (%)</label>
                    <input type="number" name="voucherDiscount" id="editVoucherDiscount" placeholder="Nhập % giảm giá" required min="0" max="100" step="1" oninput="validateDiscount(this)">
                    <label for="editVoucherValidFrom">Ngày bắt đầu</label>
                    <input type="datetime-local" name="voucherValidFrom" id="editVoucherValidFrom" required onchange="validateVoucherDates()">
                    <label for="editVoucherValidTo">Ngày kết thúc</label>
                    <input type="datetime-local" name="voucherValidTo" id="editVoucherValidTo" required onchange="validateVoucherDates()">

                    <input type="hidden" name="type" value="voucher">

                    <!-- Nút Lưu và Hủy được căn ngang hàng -->
                    <div class="form-group-inline">
                        <button type="submit">Lưu</button>
                        <button type="button" onclick="closeEditVoucherPopup()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
