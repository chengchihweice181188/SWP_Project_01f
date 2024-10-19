<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">        
        <title>Giỏ Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <style>
            body {
                background-color: #f0f0f0;
                padding: 0;
            }
            .background-container {
                background-color: #fff9f0;
                padding: 80px 0;
            }
            .cart-container {
                max-width: 700px;
                margin: 30px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border: 3px solid #333;
            }
            .cart-header {
                text-align: center;
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .cart-item {
                display: flex;
                justify-content: space-between;
                margin-bottom: 15px;
            }
            .cart-item img {
                width: 80px;
                height: 80px;
                border-radius: 10px;
            }
            .cart-item-details {
                flex: 1;
                margin-left: 15px;
            }
            .cart-item-title {
                font-size: 18px;
                font-weight: bold;
            }
            .cart-item-options {
                font-size: 14px;
                color: #666;
            }
            .cart-item-price {
                font-size: 16px;
                font-weight: bold;
                color: #333;
            }
            .cart-summary {
                border-top: 1px solid #ddd;
                padding-top: 15px;
                margin-top: 20px;
            }
            .cart-summary div {
                display: flex;
                justify-content: space-between;
            }
            .order-btn {
                display: block;
                width: 100%;
                padding: 10px;
                background-color: orange;
                color: white;
                border: none;
                font-size: 18px;
                font-weight: bold;
                border-radius: 5px;
                text-align: center;
            }
            .order-btn:hover {
                background-color: darkorange;
            }
        </style>
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
        <div class="background-container">
            <div class="cart-container">
                <div class="cart-header">Giỏ Hàng</div>
                <c:if test="${not empty cartItems}">
                    <c:forEach var="cartItem" items="${cartItems}" varStatus="status">
                        <c:set var="product" value="${products[status.index]}" />
                        <div class="cart-item">
                            <img src="/ProductImg/${product.product_image}" alt="${product.product_name}">
                            <div class="cart-item-details">
                                <div class="cart-item-title">${product.product_name}</div>
                                <div class="cart-item-price">${product.product_price * cartItem.quantity}đ</div>
                                <div class="cart-item-price">SL: ${cartItem.quantity}</div>
                            </div>
                            <form action="/Cart" method="POST">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="cartItemId" value="${cartItem.cart_item_id}">
                                <button type="submit" class="btn btn-danger">Xóa</button>
                            </form>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty cartItems}">
                    <p>Giỏ hàng của bạn hiện tại không có sản phẩm nào.</p>
                </c:if>
                <div class="form-group">
                    <label for="note">Ghi chú:</label>
                    <input type="text" class="form-control" id="note" placeholder="">
                </div>
                <div class="form-group mt-2">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="usePoints" value="usePoints">
                        <label class="form-check-label" for="usePoints">Dùng mã</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="text" class="form-control" id="discountCode" placeholder="Mã giảm giá">
                    </div>
                </div>
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="pickup" value="pickup">
                    <label class="form-check-label" for="pickup">Đến lấy</label>
                </div>
                <div class="form-group mt-2">
                    <label for="address">Địa chỉ giao hàng:</label>
                    <input type="text" class="form-control" id="address" placeholder="">
                </div>
                <button class="btn btn-outline-secondary mt-3">Phương thức thanh toán</button>
                <c:if test="${cartSummary != null}">
                    <div class="cart-summary mt-3">
                        <div><span>Giá gốc:</span><span>${cartSummary.originalPrice}đ</span></div>
                        <div><span>Giảm giá:</span><span>${cartSummary.discount}đ</span></div>
                        <div><span>Phí giao hàng:</span><span>${cartSummary.shippingFee}đ</span></div>
                        <div class="mt-2"><span>Tổng cộng:</span><span class="cart-item-price">${cartSummary.total}đ</span></div>
                    </div>
                </c:if>
                <button class="order-btn mt-3">Đặt hàng</button>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>
