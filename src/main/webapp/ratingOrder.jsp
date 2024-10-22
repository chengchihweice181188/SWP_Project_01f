<%-- 
    Document   : ratingOrder
    Created on : Oct 19, 2024, 12:59:14 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>Đánh giá sản phẩm</title>
        <link rel="stylesheet" href="./assets/stylesheet/ratingOrder.css">
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
        <div class="container">
            <h2>Đánh giá sản phẩm</h2>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    <%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : ""%>
                </div>
            </c:if>
            <form action="CreateRating" method="POST">
                <!-- Star rating section -->
                <div class="rating-section">
                    <div class="rating-label">Chất lượng sản phẩm</div>
                    <div class="star-rating">
                        <input type="radio" name="rating" id="star5" value="5">
                        <label for="star5" title="Tuyệt vời">★</label>
                        <input type="radio" name="rating" id="star4" value="4">
                        <label for="star4" title="Tốt">★</label>
                        <input type="radio" name="rating" id="star3" value="3">
                        <label for="star3" title="Trung bình">★</label>
                        <input type="radio" name="rating" id="star2" value="2">
                        <label for="star2" title="Tệ">★</label>
                        <input type="radio" name="rating" id="star1" value="1">
                        <label for="star1" title="Rất tệ">★</label>   
                    </div>
                </div>

                <!-- Feedback content -->
                <div class="input-group">
                    <textarea id="review" name="review" rows="4" placeholder="Nhập nội dung đánh giá"></textarea>
                </div>

                <!-- Hidden input for order ID -->
                <input type="hidden" name="order_id" value="${order_id}">

                <!-- Buttons -->
                <!-- Nút Hủy và Xác nhận -->
                <div class="button-group">
                    <!-- Nút Hủy sẽ hiển thị xác nhận trước khi quay về trang trước -->
                    <button type="button" class="cancel-btn" onclick="confirmCancel()">Hủy</button>

                    <!-- Nút Xác nhận để gửi đánh giá -->
                    <button type="submit" class="submit-btn">Xác nhận</button>
                </div>
            </form>
        </div>
        <c:if test="${not empty successMessage}">
            <script>
                setTimeout(function () {
                    window.location.href = 'OrderList';
                }, 3000); // Chuyển sau 3 giây
            </script>
        </c:if>
        <script>
            function confirmCancel() {
                // Hộp thoại xác nhận
                if (confirm("Bạn có chắc chắn muốn hủy đánh giá này không?")) {
                    // Nếu người dùng chọn OK, quay lại trang trước
                    window.history.back();
                }
                // Nếu người dùng chọn Cancel, không làm gì cả (ở lại trang hiện tại)
            }
        </script>
    </body>
</html>


