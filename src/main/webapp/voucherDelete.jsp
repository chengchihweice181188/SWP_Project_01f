<%-- 
    Document   : voucherDelete
    Created on : Oct 12, 2024, 3:46:16 PM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xóa Voucher</title>
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
                text-align: center;
                position: relative;
            }

            .popup h2 {
                margin-top: 0;
            }

            .popup p {
                margin-bottom: 20px;
            }

            .popup form {
                display: flex;
                justify-content: space-between; /* Căn hai nút đối xứng */
                align-items: center;
                gap: 10px;
            }

            .popup form button {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                flex: 1; /* Đảm bảo nút chiếm cùng chiều rộng */
                max-width: 45%; /* Giới hạn chiều rộng nút */
            }

            .popup form button[type="submit"] {
                background-color: #f44336;
                color: white;
            }

            .popup form button[type="button"] {
                background-color: #4CAF50;
                color: white;
            }

            .popup form button:hover {
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
            .popup form .form-group-inline {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }


        </style>
        <script>
            // Hàm mở popup xóa Voucher
            function openDeleteVoucherPopup(id) {
                document.getElementById('deleteVoucherOverlay').style.display = 'flex';
                document.getElementById('deleteVoucherId').value = id;
            }

            // Hàm đóng popup xóa Voucher
            function closeDeleteVoucherPopup() {
                document.getElementById('deleteVoucherOverlay').style.display = 'none';
            }
        </script>
    </head>
    <body>
        <div id="deleteVoucherOverlay" class="overlay">
            <div class="popup">
                <span class="close-btn" onclick="closeDeleteVoucherPopup()">&#10006;</span>
                <h2>Xóa Voucher</h2>
                <p>Bạn có chắc chắn muốn xóa Voucher này?</p>
                <form action="${pageContext.request.contextPath}/DeletePromotionServlet" method="post">
                    <input type="hidden" name="VoucherId" id="deleteVoucherId">
                    <input type="hidden" name="type" value="voucher">

                    <!-- Nút Xóa và Hủy được căn ngang hàng -->
                    <div class="form-group-inline">
                        <button type="submit">Xóa</button>
                        <button type="button" onclick="closeDeleteVoucherPopup()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>
