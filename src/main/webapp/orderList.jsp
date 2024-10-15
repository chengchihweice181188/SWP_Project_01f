<%-- 
    Document   : orderList
    Created on : Oct 13, 2024, 4:20:51 PM
    Author     : Bang
--%>

<%@page import="java.util.List"%>
<%@page import="Model.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/orderList.css">
        <title>Danh sách đánh giá đơn hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>

            /* Body và cấu trúc tổng quan */
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }

            /* Sidebar Styles */
            .sidebar {
                background-color: orange;
                color: white;
                padding: 20px;
                height: 100vh;
                position: fixed;
                width: 220px;
                top: 0;
                left: 0;
            }

            .sidebar .logo img {
                max-width: 80%;
                margin-bottom: 30px;
            }

            .sidebar .menu-list {
                list-style-type: none;
                padding: 0;
            }

            .sidebar .menu-list li {
                margin-bottom: 15px;
            }

            .sidebar .menu-list li a {
                text-decoration: none;
                color: white;
                font-size: 18px;
                display: block;
                padding: 10px;
                border-radius: 5px;
            }

            .sidebar .menu-list li a:hover {
                background-color: #495057;
            }

            .sidebar button.btn-danger {
                width: 100%;
                margin-top: 20px;
            }

            /* Nội dung chính */
            .container {
                margin-left: 250px; /* Đảm bảo nội dung không đè lên sidebar */
                padding: 20px;
            }

            h1.mt-4 {
                font-size: 2.2rem;
                color: #343a40;
                font-weight: bold;
                margin-bottom: 30px;
            }

            /* Bảng đánh giá */
            .table {
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
            }

            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }

            .table thead th {
                background-color: #343a40;
                color: white;
                font-size: 1.1rem;
            }

            .table tbody tr:nth-child(odd) {
                background-color: #f8f9fa;
            }

            .table tbody tr:nth-child(even) {
                background-color: #e9ecef;
            }

            .btn-primary {
                background-color: #007bff;
                border: none;
                border-radius: 5px;
                padding: 8px 12px;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            /* Modal */
            .modal-header {
                background-color: #343a40;
                color: white;
            }

            .modal-title {
                font-size: 1.5rem;
            }

            .modal-body p {
                font-size: 16px;
                color: #555;
                margin-bottom: 10px;
            }

            .modal-footer {
                display: flex;
                justify-content: center;
            }

            .btn-secondary {
                background-color: #6c757d;
                border: none;
                padding: 8px 15px;
                font-size: 16px;
                border-radius: 5px;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }


        </style>
    </head>
    <body>
        <div class="container">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="logo">
                    <img src="food_logo.png" alt="Food Logo">
                </div>
                <ul class="menu-list">
                    <li><a href="${pageContext.request.contextPath}/Category">Quản lý danh mục</a></li>
                    <li><a href="${pageContext.request.contextPath}/Product">Quản lý sản phẩm</a></li>
                    <li><a href="${pageContext.request.contextPath}/Option">Quản lý lựa chọn</a></li>
                    <li><a href="${pageContext.request.contextPath}/Promotion">Quản lý khuyến mãi</a></li>
                    <li><a href="${pageContext.request.contextPath}/Revenue">Thống kê doanh thu</a></li>
                    <li><a href="${pageContext.request.contextPath}/ProductStats">Thống kê sản phẩm</a></li>
                    <li><a href="${pageContext.request.contextPath}/OrderFeedback">Xem danh sách đánh giá</a></li>
                    <li><a href="${pageContext.request.contextPath}/Account">Quản lí tài khoản</a></li>
                </ul>
                <button class="btn btn-danger">Logout</button>
            </div>
            <h1 class="mt-4">Danh sách đánh giá của người dùng</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Đơn hàng</th>
                        <th>Tên khách hàng</th>
                        <th>Điểm đánh giá</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Order> ordersWithFeedback = (List<Order>) request.getAttribute("ordersWithFeedback");
                        for (Order order : ordersWithFeedback) {
                    %>
                    <tr>
                        <td><%= order.getOrderId()%></td>
                        <td><%= order.getUserName()%></td>
                        <td><%= order.getFeedbackRating()%> / 5</td>
                        <td>
                            <button class="btn btn-primary" onclick="showDetails(<%= order.getOrderId()%>)">Chi tiết</button>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table
        </div>
        <!-- Modal để hiển thị chi tiết đơn hàng -->
        <div class="modal fade" id="orderDetailsModal" tabindex="-1" aria-labelledby="orderDetailsModalLabel" aria-hidden="true" >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="orderDetailsModalLabel">Chi tiết đơn hàng</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Nội dung chi tiết đơn hàng sẽ được chèn vào đây -->
                        <p><strong>ID Đơn hàng:</strong> <span id="orderId"></span></p>
                        <p><strong>Tên khách hàng:</strong> <span id="username"></span></p>
                        <p><strong>Điểm đánh giá:</strong> <span id="feedbackRating"></span></p>
                        <p><strong>Bình luận:</strong> <span id="feedbackComment"></span></p>
                        <p><strong>Ngày đặt hàng:</strong> <span id="orderDate"></span></p>
                        <p><strong>Trạng thái:</strong> <span id="orderStatus"></span></p>
                        <p><strong>Phương thức thanh toán:</strong> <span id="paymentMethod"></span></p>
                        <p><strong>Giá trị đơn hàng:</strong> <span id="orderPrice"></span> VND</p> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showDetails(orderId) {
                $.ajax({
                    url: '/OrderDetails',
                    method: 'Get',
                    data: {orderId: orderId},
                    dataType: 'json',
                    success: function (data) {
                        console.log(data);
                        // Đổ dữ liệu vào modal
                        $('#orderId').text(data.orderId);
                        $('#username').text(data.userName);
                        $('#feedbackRating').text(data.feedbackRating + ' / 5');
                        $('#feedbackComment').text(data.feedbackComment);
                        $('#orderDate').text(data.orderDate);
                        $('#orderStatus').text(data.orderStatus);
                        $('#paymentMethod').text(data.paymentMethod);
                        $('#orderPrice').text(data.orderPrice);

                        // Hiển thị modal   
                        $('#orderDetailsModal').modal('show');
                    }
                });
            }
        </script>
    </script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
