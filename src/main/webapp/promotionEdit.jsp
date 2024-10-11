<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<sql:setDataSource var="dataSource"
                   driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                   url="jdbc:sqlserver://ASUS3DK:1433;databaseName=swp_pj_04f;encrypt=true;trustServerCertificate=true"
                   user="sa"
                   password="sa123" />

<sql:query dataSource="${dataSource}" var="productList">
    SELECT product_id, product_name FROM Products WHERE is_hidden = 0
</sql:query>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa Khuyến Mãi</title>
        <style>
            /* CSS của bạn */
        </style>
        <script>
            // Hàm mở popup chỉnh sửa khuyến mãi
            function openEditPromotionPopup(id, discount, validFrom, validTo, productId) {
                console.log('Received data:', {id, discount, validFrom, validTo, productId}); // Debugging
                document.getElementById('editPromotionOverlay').style.display = 'flex';
                document.getElementById('editPromotionId').value = id;
                document.getElementById('editPromotionDiscount').value = discount;
                document.getElementById('editPromotionValidFrom').value = validFrom;
                document.getElementById('editPromotionValidTo').value = validTo;
                document.getElementById('editProductId').value = productId;
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


                    // Hàm đóng popup chỉnh sửa khuyến mãi
                    function closeEditPromotionPopup() {
                        document.getElementById('editPromotionOverlay').style.display = 'none';
                    }
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
                    function validatePromotionDates() {
                        const startDateInput = document.getElementById('editPromotionValidFrom');
                        const endDateInput = document.getElementById('editPromotionValidTo');
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
        <div id="editPromotionOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeEditPromotionPopup()">&#10006;</span>
                <h2>Chỉnh sửa Khuyến Mãi</h2>
                <form action="${pageContext.request.contextPath}/UpdatePromotionServlet" method="post">
                    <input type="hidden" name="promotionId" id="editPromotionId">
                    <input type="number" name="promotionDiscount" id="editPromotionDiscount" placeholder="Nhập % giảm giá" required min="0" max="100" step="1" oninput="validateDiscount(this)">
                    <input type="datetime-local" name="promotionValidFrom" id="editPromotionValidFrom" required onchange="validatePromotionDates()">
                    <input type="datetime-local" name="promotionValidTo" id="editPromotionValidTo" required onchange="validatePromotionDates()">

                    <select name="productId" id="editProductId" required>
                        <c:forEach var="product" items="${productList.rows}">
                            <option value="${product.product_id}">${product.product_name}</option>
                        </c:forEach>
                    </select>
                    <button type="submit">Lưu</button>
                    <button type="button" onclick="closeEditPromotionPopup()">Hủy</button>
                </form>
            </div>
        </div>
    </body>
</html>
