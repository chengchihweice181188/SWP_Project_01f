<%-- 
    Document   : viewRatingOrder
    Created on : Oct 19, 2024, 1:22:55 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

            <!-- Form to submit updated rating and review -->
            <form action="ViewRating" method="POST">
                <!-- Star rating section -->
                <div class="rating-section">
                    <div class="rating-label">Chất lượng sản phẩm</div>
                    <div class="star-rating">
                        <!-- Initially disabled for view mode -->
                        <input type="radio" name="rating" id="star5" value="5" disabled <c:if test="${rating == 5}">checked</c:if>>
                            <label for="star5" title="Tuyệt vời">★</label>

                            <input type="radio" name="rating" id="star4" value="4" disabled <c:if test="${rating == 4}">checked</c:if>>
                            <label for="star4" title="Tốt">★</label>

                            <input type="radio" name="rating" id="star3" value="3" disabled <c:if test="${rating == 3}">checked</c:if>>
                            <label for="star3" title="Trung bình">★</label>

                            <input type="radio" name="rating" id="star2" value="2" disabled <c:if test="${rating == 2}">checked</c:if>>
                            <label for="star2" title="Tệ">★</label>

                            <input type="radio" name="rating" id="star1" value="1" disabled <c:if test="${rating == 1}">checked</c:if>>
                            <label for="star1" title="Rất tệ">★</label>   
                        </div>
                    </div>

                    <!-- Feedback content -->
                    <div class="input-group">
                        <textarea id="review" name="review" rows="4" placeholder="Nhập nội dung đánh giá" readonly>${review}</textarea>
                </div>

                <!-- Hidden input for order ID -->
                <input type="hidden" name="order_id" value="${order_id}">

                <div class="button-group">
                    <!-- Initial "Chỉnh sửa" button -->
                    <button type="button" class="submit-btn" id="editBtn" onclick="enableEdit()">Chỉnh sửa</button>

            </form>
            <!-- Form for deleting the rating -->
            <form action="DeleteRating" method="POST">
                <!-- Hidden input to pass the order ID -->
                <input type="hidden" name="order_id" value="${order_id}">

                <!-- Button for deleting the rating -->
                <button type="submit" class="cancel-btn" id="deletetBtn" onclick="confirmCancel()">Xóa</button>
            </form>
        </div>
    </div>
    <c:if test="${not empty successMessage}">
        <script>
            setTimeout(function () {
                window.location.href = 'OrderList';
            }, 3000); // Chuyển sau 3 giây
        </script>
    </c:if>        
    <script>
        let isEditing = false; // To track if user is editing

        function enableEdit() {
            if (!isEditing) {
                // Prevent form submission and enable edit mode
                event.preventDefault();  // This prevents the form from submitting on first click

                // Enable stars for editing
                document.querySelectorAll('input[type="radio"]').forEach(el => el.disabled = false);
                // Enable textarea for editing
                document.getElementById('review').removeAttribute('readonly');

                // Change the button label to "Lưu" and allow form submission on the next click
                const editBtn = document.getElementById('editBtn');
                editBtn.textContent = "Lưu";
                editBtn.setAttribute("type", "submit");

                isEditing = true; // Set the state to editing
            }
        }
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
