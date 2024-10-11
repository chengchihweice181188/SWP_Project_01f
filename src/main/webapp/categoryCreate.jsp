<%-- 
    Document   : categoryCreate
    Created on : Oct 10, 2024, 12:58:26 PM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Danh Mục Sản Phẩm</title>
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
            .popup img {
                width: 100px;
                margin-bottom: 10px;
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
            // Hàm mở popup thêm danh mục
            function openCreatePopup() {
                document.getElementById('createPopupOverlay').style.display = 'flex';
            }

            // Hàm đóng popup thêm danh mục
            function closeCreatePopup() {
                document.getElementById('createPopupOverlay').style.display = 'none';
            }
        </script>
    </head>
    <body>
        <div id="createPopupOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeCreatePopup()">&#10006;</span>
                <h2>Thêm danh mục sản phẩm</h2>
                <img src="path-to-your-image/fast-food.png" alt="Fast Food">
                <form action="AddCategoryServlet" method="post">
                    <input type="text" name="categoryName" placeholder="Nhập tên danh mục" required>
                    <button type="submit">Thêm</button>
                </form>
            </div>
        </div>
    </body>
</html>
