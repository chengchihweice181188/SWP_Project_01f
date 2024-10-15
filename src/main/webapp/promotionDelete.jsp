<%-- 
    Document   : promotionDelete
    Created on : Oct 10, 2024, 3:30:00 PM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xóa Khuyến Mãi</title>
        <style>
            /* Làm mờ nền khi popup mở */
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

            /* Popup chính với bo tròn và bóng đổ */
            .popup {
                background-color: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
                max-width: 400px;
                width: 90%;
                position: relative;
                text-align: center;
            }

            /* Tiêu đề popup */
            .popup h2 {
                margin-top: 0;
                font-size: 24px;
                color: #333;
                font-weight: bold;
            }

            /* Đoạn văn bản thông báo */
            .popup p {
                margin: 20px 0;
                font-size: 16px;
                color: #555;
            }

            /* Căn chỉnh các nút với flexbox */
            .popup form {
                display: flex;
                justify-content: space-between;
                gap: 10px;
                margin-top: 20px;
            }

            /* Thiết kế các nút */
            .popup form button {
                padding: 12px 20px;
                font-size: 16px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                width: 48%;
            }

            /* Nút Xóa với màu đỏ */
            .popup form button[type="submit"] {
                background-color: #f44336;
                color: white;
                transition: background-color 0.3s;
            }

            /* Nút Hủy với màu xanh lá cây */
            .popup form button[type="button"] {
                background-color: #4CAF50;
                color: white;
                transition: background-color 0.3s;
            }

            /* Hiệu ứng hover cho các nút */
            .popup form button[type="submit"]:hover {
                background-color: #d32f2f;
            }

            .popup form button[type="button"]:hover {
                background-color: #388e3c;
            }

            /* Nút đóng popup (dấu X) */
            .close-btn {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 20px;
                color: #555;
                cursor: pointer;
                background: none;
                border: none;
                transition: color 0.3s;
            }

            .close-btn:hover {
                color: #000;
            }
            .popup form .form-group-inline {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }


        </style>
        <script>
            // Hàm mở popup xóa khuyến mãi
            function openDeletePromotionPopup(id) {
                document.getElementById('deletePromotionOverlay').style.display = 'flex';
                document.getElementById('deletePromotionId').value = id;
            }

            // Hàm đóng popup xóa khuyến mãi
            function closeDeletePromotionPopup() {
                document.getElementById('deletePromotionOverlay').style.display = 'none';
            }
        </script>
    </head>
    <body>
        <div id="deletePromotionOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeDeletePromotionPopup()">&#10006;</span>
                <h2>Xóa Khuyến Mãi</h2>
                <p>Bạn có chắc chắn muốn xóa khuyến mãi này?</p>
                <form action="${pageContext.request.contextPath}/DeletePromotionServlet" method="post">
                    <input type="hidden" name="promotionId" id="deletePromotionId">
                    <input type="hidden" name="type" value="promotion">

                    <div class="form-group-inline">
                        <button type="submit">Xóa</button>
                        <button type="button" onclick="closeDeletePromotionPopup()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
