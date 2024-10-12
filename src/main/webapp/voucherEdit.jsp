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
            /* CSS của bạn */
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
                    <input type="text" name="voucherCode" id="editVoucherCode" placeholder="Mã voucher" required>
                    <input type="number" name="voucherDiscount" id="editVoucherDiscount" placeholder="Nhập % giảm giá" required min="0" max="100" step="1" oninput="validateDiscount(this)">
                    <input type="datetime-local" name="voucherValidFrom" id="editVoucherValidFrom" required onchange="validateVoucherDates()">
                    <input type="datetime-local" name="voucherValidTo" id="editVoucherValidTo" required onchange="validateVoucherDates()">
                    <input type="hidden" name="type" value="voucher">


                    <button type="submit">Lưu</button>
                    <button type="button" onclick="closeEditVoucherPopup()">Hủy</button>
                </form>
            </div>
        </div>
    </body>
</html>
