<%-- 
    Document   : Homepage
    Created on : Oct 6, 2024, 2:01:06 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>
            .category {
                border-top: solid;
                border-bottom: solid;
                border-width: 2px;
                text-align: center;
                font-size: 30px;
                white-space: nowrap;      /* Không cho phép xuống dòng */
                overflow-x: auto;         /* Thêm thanh cuộn ngang khi cần */
                width: 100%;
                background: lightgray;
            }
            .btn-edit{
                width: 100%;
                height: 11.2%
            }
            .product-container {
                display: flex;
                flex-wrap: wrap;/* Cho phép xuống dòng khi không đủ không gian */
                max-width: 100%;
                margin: 70px;
                margin-top: 30px;
                margin-left: 80px;
            }
            .product-item {
                width: 19%;/* Đặt độ rộng cho mỗi khối chiếm khoảng 1/4 dòng */
                height: 329px;
                margin: 6px;
                border: 2px solid #ccc;
                border-radius: 7px;
            }
            .img {
                width: 100%;
                height: 250px;
                object-fit: cover;
                padding: 5px;
            }
            .product-name {
                text-align: center;
                white-space: nowrap;
                overflow: hidden;/* Ẩn nội dung vượt quá chiều rộng */
                text-overflow: ellipsis;/* Hiển thị dấu "..." khi nội dung quá dài */
                font-weight: bold;
            }
            .text{
                text-decoration: none;
                color: inherit;
            }
            .footer-edit{
                background: black;
                color: white;
                padding-left: 80px;
                position: fixed;
                bottom: 0;
                width: 100%;
            }
            .margin-padding{
                margin: 0px;
                padding: 0px;
                display: flex;
                justify-content: space-between;
            }
            .pop-up {
                display: none;
                position: fixed;
                width: 600px;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: white;
                border: 5px solid #ccc;
                border-radius: 10px;
                z-index: 100; /* Đặt z-index 100 để nằm trên overlay */
            }
            .detail{
                display: flex;
                flex-direction: column;
                justify-content: center; /* Căn đều các phần tử theo chiều dọc */
            }
            .overlay {
                display: none; /* Ẩn overlay khi popup chưa hiển thị */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5); /* Làm mờ không gian xung quanh */
                z-index: 99; /* Đặt z-index cao để overlay hiển thị trên cùng */
            }
            .full-width{
                width: 100%;
                margin-bottom: 20px;
            }
            .wrap-text {
                width: 100%; /* Đặt chiều rộng của div */
                word-wrap: break-word; /* Đảm bảo từ ngữ tự động xuống dòng khi vượt quá chiều rộng */
                white-space: normal; /* Cho phép xuống dòng khi hết không gian */
            }
            .no-product{
                display: flex;
                text-align: center;
                margin-top: 20px;
                justify-content: center; /* Căn giữa theo chiều ngang */
            }
            .background{
                width: 100%;
                height: 225px;
                object-fit: cover;
            }
        </style>
        <script>
            function showPopup(image, name, description, price, productId) {
                // Hiển thị popup dựa trên productId
                document.getElementById("popup_" + productId).style.display = "flex";
                // Hiển thị overlay
                document.getElementById("overlay").style.display = "block";
                // Gán thông tin chi tiết của sản phẩm vào popup dựa trên productId
                document.getElementById("popupImage_" + productId).src = "/ProductImg/" + image;
                document.getElementById("popupName_" + productId).innerText = name;
                document.getElementById("popupDescription_" + productId).innerText = description;
                // Gọi hàm updatePrice với số lượng mặc định là 1 và tùy chọn mặc định là 0 (không điều chỉnh giá)
                updatePrice(price, productId, 1, 0);
            }
            function closePopup(productId) {
                // Ẩn popup dựa trên productId
                document.getElementById("popup_" + productId).style.display = "none";
                document.getElementById("overlay").style.display = "none";
            }
            function updatePrice(basePrice, productId, quantity, optionPriceAdjustment) {
                // Chuyển đổi các giá trị đầu vào thành số nguyên để tính toán
                var quantityValue = parseInt(quantity);
                var optionPrice = parseInt(optionPriceAdjustment);
                // Đảm bảo rằng giá trị điều chỉnh của option là số, nếu không thì mặc định là 0
                if (isNaN(optionPrice)) {
                    optionPrice = 0;
                }
                // Tính toán giá cuối cùng
                var finalPrice = (parseInt(basePrice) + optionPrice) * quantityValue;
                // Cập nhật giá trị trong nút "Thêm"
                document.getElementById("popupPrice_" + productId).innerText = "Thêm: " + finalPrice + "đ";
                // Cập nhật giá trị vào input ẩn để khi submit form có giá trị này
                document.getElementById("finalPrice_" + productId).value = finalPrice;
            }
        </script>
    </head>
    <body style="background: #fff9f0">
        <%@ include file="navbar.jsp" %> 
        <img class="background" src="/WebLogo/background.jpg">
        <div class="category">
            &nbsp;<a href="/" class="text">Tất cả</a> &nbsp;
            <c:forEach var="categoryVar" items="${categoryList}">
                &nbsp;<a href="/ViewCategoryController/Category/${categoryVar.category_id}" class="text">${categoryVar.category_name}</a> &nbsp;
            </c:forEach>
        </div>
        <c:if test="${empty productList}">
            <h2 class="no-product">Hiện không có sản phẩm nào trong mục này</h2>
        </c:if>
        <div class="product-container">
            <c:if test="${!empty productList}">
                <c:forEach var="productVar" items="${productList}">
                    <div class="product-item">
                        <img class="img" src="/ProductImg/${productVar.product_image}" alt="${productVar.product_name}">
                        <p class="product-name">${productVar.product_name}</p>
                        <!--truyền tham số vào dđể khi bấm sẽ hiện ra popup tương ứng-->
                        <button class="btn btn-success btn-edit" onclick="showPopup('${productVar.product_image}',
                                        '${productVar.product_name}',
                                        '${productVar.product_description}',
                                        '${productVar.product_price}',
                                        '${productVar.product_id}'
                                        )">Chọn: ${productVar.product_price}đ</button>
                    </div>
                    <div class="pop-up row" id="popup_${productVar.product_id}" style="display:none;">
                        <div class="col">
                            <img class="img" id="popupImage_${productVar.product_id}" src="" alt="">
                            <p class="wrap-text product-name" id="popupName_${productVar.product_id}"></p>
                            <p class="wrap-text" id="popupDescription_${productVar.product_id}"></p>
                        </div>
                        <div class="col detail">
                            <h3 style="text-align: center">Lựa chọn</h3>
                            <form method="get" action="">
                                Số lượng:
                                <input type="number" class="full-width" id="popupQuantity_${productVar.product_id}" name="productQuantity" value="1" min="1" max="99" step="1" required onkeydown="return false;"
                                       onchange="updatePrice('${productVar.product_price}', '${productVar.product_id}', this.value, document.getElementById('popupOption_${productVar.product_id}').value)"/><br>
                                Tùy chỉnh:
                                <select name="productOptionId" class="full-width" id="popupOption_${productVar.product_id}"
                                        onchange="updatePrice('${productVar.product_price}', '${productVar.product_id}', document.getElementById('popupQuantity_${productVar.product_id}').value, this.value)">
                                    <option value="0">Mặc định</option>
                                    <c:forEach var="optionVar" items="${productVar.options}">
                                        <option value="${optionVar.price_adjustment}">${optionVar.option_name} - ${optionVar.price_adjustment}đ</option>
                                    </c:forEach>
                                </select>
                                <!-- Thêm một input ẩn để lưu giá cuối cùng để xử lí-->
                                <input type="hidden" id="finalPrice_${productVar.product_id}" name="finalPrice" value="">
                                <button type="submit" class="btn btn-success full-width" id="popupPrice_${productVar.product_id}"></button>
                            </form>
                            <button class="btn btn-danger full-width" onclick="closePopup('${productVar.product_id}')">Quay lại</button>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div class="overlay" id="overlay"></div>
        <footer class="footer-edit">
            <ul class="margin-padding">
                <li>Email: swpffshopgroup@gmail.com</li>
                <li>Số điện thoại: </li>
                <li>Địa chỉ: </li>
            </ul>
        </footer>
    </body>
</html>
