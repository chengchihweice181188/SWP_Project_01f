<%-- 
    Document   : categoryEdit
    Created on : Oct 10, 2024, 1:48:29 PM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa Danh Mục Sản Phẩm</title>
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
                width: 400px;
                padding: 20px;
                background: #f0f0f0;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                position: relative;
                text-align: center;
            }
            .popup input[type="text"] {
                width: calc(100% - 20px);
                padding: 10px;
                margin-top: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }
            .popup button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
            }
            .popup button:hover {
                background-color: #45a049;
            }
            .popup h2 {
                color: #d35400;
                font-size: 18px;
            }
            .close-btn {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 18px;
                color: red;
                cursor: pointer;
            }
        </style>
        <script>
            // Hàm để mở popup chỉnh sửa
            function openEditPopup(itemId, currentName) {
                document.getElementById('editPopupOverlay').style.display = 'flex';
                document.getElementById('editCategoryName').value = currentName;
                document.getElementById('editItemId').value = itemId;
            }

            // Hàm để đóng popup chỉnh sửa
            function closeEditPopup() {
                document.getElementById('editPopupOverlay').style.display = 'none';
            }
        </script>
    </head>
    <body>
        <!-- Popup Chỉnh sửa Danh Mục -->
        <div id="editPopupOverlay" class="overlay" style="display: none;">
            <div class="popup">
                <span class="close-btn" onclick="closeEditPopup()">&#10006;</span>
                <h2>Chỉnh sửa danh mục sản phẩm</h2>
                <form action="EditCategoryServlet" method="post">
                    <input type="hidden" id="editItemId" name="itemId" value="">
                    <input type="text" id="editCategoryName" name="categoryName" value="" placeholder="Nhập tên danh mục mới" required>
                    <button type="submit">Lưu</button>
                    <button type="button" onclick="closeEditPopup()">Hủy</button>
                </form>
            </div>
        </div>
    </body>
</html>