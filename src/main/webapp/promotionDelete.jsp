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
                    <button type="submit">Xóa</button>
                    <button type="button" onclick="closeDeletePromotionPopup()">Hủy</button>
                </form>
            </div>
        </div>
    </body>
</html>
