<%-- 
    Document   : categoryDelete
    Created on : Oct 10, 2024, 1:12:24 PM
    Author     : Bang
--%>

<%-- 
    Document   : categoryDelete
    Created on : Oct 10, 2024, 1:12:24 PM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xác nhận Xóa</title>
        <style>
            .overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: none; /* Ẩn đi lúc đầu */
                justify-content: center;
                align-items: center;
                z-index: 1000; /* Đảm bảo popup luôn nằm trên cùng */
            }

            .popup {
                width: 300px;
                padding: 20px;
                background: #f0f0f0;
                border-radius: 10px;
                text-align: center;
                position: relative;
            }

            .close-btn {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 18px;
                color: red;
                cursor: pointer;
            }

            .popup button {
                background-color: #d9534f;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin: 10px 5px;
            }

            .popup button:hover {
                background-color: #c9302c;
            }

            .popup h2 {
                color: #d35400;
                font-size: 18px;
                margin-bottom: 20px;
            }
        </style>
        <script>
            // Mở popup và thiết lập nội dung động
            function openPopup(itemId, itemName) {
                document.getElementById('deletePopup').style.display = 'flex';
                document.getElementById('deleteMessage').innerText = `Bạn có chắc chắn muốn xóa ${itemName}?`;
                document.getElementById('itemId').value = itemId; // Gán itemId vào input hidden
            }

            // Đóng popup
            function closePopup() {
                document.getElementById('deletePopup').style.display = 'none';
            }
        </script>
    </head>
    <body>
        <!-- Popup Xác nhận Xóa -->
        <div class="overlay" id="deletePopup" style="display: none;">
            <div class="popup">
                <span class="close-btn" onclick="closePopup()">&#10006;</span>
                <h2 id="deleteMessage">Bạn có chắc chắn muốn xóa?</h2>
                <form action="DeleteCategoryServlet" method="post">
                    <input type="hidden" name="itemId" id="itemId">
                    <button type="submit">Xác nhận</button>
                    <button type="button" onclick="closePopup()">Hủy</button>
                </form>
            </div>
        </div>

    </body>
</html>
