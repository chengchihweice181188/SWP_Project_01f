<%-- 
    Document   : ChoosedCategory
    Created on : Oct 10, 2024, 10:49:52 AM
    Author     : LENOVO
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.ViewCategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .category {
                border-bottom: solid;
                border-width: 2px;
                text-align: center;
                font-size: 30px;
                white-space: nowrap;      /* Không cho phép xuống dòng */
                overflow-x: auto;         /* Thêm thanh cuộn ngang khi cần */
                width: 100%;
                margin-top: -2px;
            }
            .btn-edit{
                width: 100%;
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
                width: 256px;
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
                margin-top: 10px;
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
            }
            function closePopup(productId) {
                // Ẩn popup dựa trên productId
                document.getElementById("popup_" + productId).style.display = "none";
                document.getElementById("overlay").style.display = "none";
            }
        </script>
    </head>
    <body style="background: #fff9f0">
        <%@ include file="navbar.jsp" %> 
        <br><br><br>
        <div class="category">
            &nbsp;<a href="/index.jsp" class="text">Tất cả</a> &nbsp;
            <%
                ViewCategoryDAO dao = new ViewCategoryDAO();
                ResultSet rs = dao.getAllCategories();
                if (rs != null) {
                    while (rs.next()) {
            %>
            &nbsp;<a href="/ViewCategoryController/Category/<%=rs.getString("category_id")%>" class="text"><%=rs.getString("category_name")%></a> &nbsp;
            <%
                    }
                }
            %>
        </div>
        <c:if test="${empty productList}">
            <h2 class="no-product">Hiện không có sản phẩm nào trong mục này</h2>
        </c:if>
        <div class="product-container">
            <c:if test="${!empty productList}">
                <c:forEach var="product" items="${productList}">
                    <div class="product-item">
                        <img class="img" src="/ProductImg/${product.product_image}" alt="${product.product_name}">
                        <p class="product-name">${product.product_name}</p>
                        <!--truyền tham số vào dđể khi bấm sẽ hiện ra popup tương ứng-->
                        <button class="btn btn-success btn-edit" onclick="showPopup('${product.product_image}',
                                        '${product.product_name}',
                                        '${product.product_description}',
                                        '${product.product_price}',
                                        '${product.product_id}'
                                        )">Chọn: ${product.product_price}đ</button>
                    </div>
                    <div class="pop-up row" id="popup_${product.product_id}" style="display:none;">
                        <div class="col">
                            <img class="img" id="popupImage_${product.product_id}" src="" alt="">
                            <p class="wrap-text product-name" id="popupName_${product.product_id}"></p>
                            <p class="wrap-text" id="popupDescription_${product.product_id}"></p>
                        </div>
                        <div class="col detail">
                            <form method="get" action="">
                                Số lượng:
                                <!--số lượng tối thiểu là 1, tối đa là 99.-->
                                <input type="number" class="full-width" name="productQuantity" value="1" min="1" max="99" step="1" required onkeydown="return false;"/><br>
                                Tùy chỉnh:
                                <select name="productOptionId" class="full-width">
                                    <option value="0">Mặc định</option>

                                </select>
                                <button type="submit" class="btn btn-success full-width" id="popupPrice_${product.product_id}">Thêm</button>
                                <button class="btn btn-danger full-width" onclick="closePopup('${product.product_id}')">Quay lại</button>
                            </form>
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

