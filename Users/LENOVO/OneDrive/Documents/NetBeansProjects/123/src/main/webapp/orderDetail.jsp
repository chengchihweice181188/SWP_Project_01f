<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Đơn Hàng</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h3>Chi tiết đơn hàng - ID: ${orderDetail[0].order_id}</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Hình ảnh</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detail" items="${orderDetail}" varStatus="status">
                    <c:set var="product" value="${products[status.index]}" />
                            <tr>
                                <td>${product.product_name}</td>
                                <td><img src="/ProductImg/${product.product_image}" alt="${product.product_name}" style="width: 100px; height: auto;"></td>
                                <td>${detail.quantity}</td>
                                <td>${product.product_price * detail.quantity}đ</td>
                            </tr>
                </c:forEach>
            </tbody>
        </table>
   </div>
</body>
</html>
