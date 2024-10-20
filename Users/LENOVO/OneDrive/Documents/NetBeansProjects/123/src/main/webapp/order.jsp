<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ID Đơn Hàng</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #fdf1de;
        }
        .order-container {
            max-width: 800px;
            margin: 50px auto;
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .order-header {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .order-item {
            display: flex;
            flex-direction: column;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 5px;
            margin-bottom: 10px;
        }
        .order-item:last-child {
            border: none;
        }
        .order-item-name {
            font-size: 16px;
            font-weight: bold;
        }
        .order-item-time {
            font-size: 12px;
            color: #888;
        }
        .order-item-status {
            font-size: 14px;
        }
        .status-delivered {
            color: #28a745;
        }
        .status-cancelled {
            color: #e74c3c;
        }
        .status-pending {
            color: #f39c12;
        }
        .total-price {
            font-weight: bold;
            font-size: 16px;
            margin-top: 5px;
            text-align: right;
        }
        .order-item-buttons {
            margin-top: 5px;
            display: flex;
            justify-content: flex-end;
        }
        .modal-header, .modal-footer {
            background-color: #ff7f00;
            color: white;
        }
        .modal-body {
            text-align: left;
        }
        .btn-cancel, .btn-review, .btn-detail {
            margin-left: 5px;
        }
        .stars {
            font-size: 24px;
            color: #ffcc00;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="order-container">
            <div class="order-header">Đơn Hàng</div>
            <c:forEach var="order" items="${orders}">
                <div class="order-item">
                    <div class="order-item-details">
                        <div class="order-item-name">Mã đơn hàng: ${order.order_id}</div>
                        <div class="order-item-name">${order.order_note}</div>
                        <div class="order-item-time">Đặt vào: ${order.order_date}</div>
                        <div class="order-item-status ${order.order_status == 'Đã giao' ? 'status-delivered' : (order.order_status == 'Đã hủy' ? 'status-cancelled' : 'status-pending')}">${order.order_status}</div>
                    </div>
                    <div class="total-price">${order.order_price}đ</div>
                    <div class="order-item-buttons">
                        <c:choose>
                            <c:when test="${order.order_status == 'Đã giao'}">
                                <button class="btn btn-primary btn-review" data-toggle="modal" data-target="#reviewModal${order.order_id}">Đánh giá</button>
                            </c:when>
                            <c:when test="${order.order_status == 'Đang chờ xử lý'}">
                                <form action="/Order" method="POST">
                                    <input type="hidden" name="action" value="Cancel">
                                    <input type="hidden" name="order_id" value="${order.order_id}">
                                    <button class="btn btn-danger btn-cancel" onclick="cancelOrder(${order.order_id})">Hủy đơn hàng</button>
                                </form>
                            </c:when>
                        </c:choose>
                        <button type="button" class="btn btn-info btn-detail" data-toggle="modal" data-target="#orderDetailModal" onclick="loadOrderDetail(${order.order_id})">Xem chi tiết</button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Modal for Order Details -->
        <div class="modal fade" id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="orderDetailModalLabel">Chi tiết đơn hàng</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="modalOrderDetailContent">
                        <!-- Nội dung chi tiết đơn hàng sẽ được chèn vào đây -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal for Review -->
        <c:forEach var="order" items="${orders}">
            <div class="modal fade" id="reviewModal${order.order_id}" tabindex="-1" role="dialog" aria-labelledby="reviewModal${order.order_id}Label" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="reviewModal${order.order_id}Label">Đánh giá đơn hàng - ${order.order_note}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <!-- Form to submit review -->
                        <form action="/Order" method="POST">
                            <div class="modal-body">
                                <!-- Hidden fields to pass data -->
                                <input type="hidden" name="action" value="UpdateFeedback">
                                <input type="hidden" name="order_id" value="${order.order_id}">

                                <p><strong>Vui lòng đánh giá đơn hàng:</strong></p>
                                <div class="stars">
                                    <!-- Radio buttons for rating -->
                                    <label>
                                        <input type="radio" name="feedback_rating" value="1"> ★
                                    </label>
                                    <label>
                                        <input type="radio" name="feedback_rating" value="2"> ★★
                                    </label>
                                    <label>
                                        <input type="radio" name="feedback_rating" value="3"> ★★★
                                    </label>
                                    <label>
                                        <input type="radio" name="feedback_rating" value="4"> ★★★★
                                    </label>
                                    <label>
                                        <input type="radio" name="feedback_rating" value="5"> ★★★★★
                                    </label>
                                </div>

                                <!-- Text area for feedback comment -->
                                <textarea class="form-control" name="feedback_comment" rows="4" placeholder="Viết bình luận..."></textarea>
                            </div>
                            <div class="modal-footer">
                                <!-- Submit button for the form -->
                                <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                                <button type="button" class="btn btn-light" data-dismiss="modal">Đóng</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
        
        <script>
            function loadOrderDetail(orderId) {
                $.ajax({
                    url: '/Order?action=Detail&order_id=' + orderId, 
                    type: 'GET',
                    success: function(response) {
                        $('#modalOrderDetailContent').html(response);
                    },
                    error: function(xhr, status, error) {
                        console.log('Error:', error);
                    }
                });
            }
        </script>
    </div>
</body>
</html>
