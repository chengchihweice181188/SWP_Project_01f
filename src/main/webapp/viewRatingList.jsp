<%-- 
    Document   : viewRatingList
    Created on : Oct 18, 2024, 3:51:37 PM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh Sách Đánh Giá</title>
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.7/css/dataTables.dataTables.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="./stylesheet/viewratingList.css"/> <!-- Custom CSS for additional styling -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.7/js/dataTables.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                $('#ratingTable').DataTable({
                    "language": {
                        "lengthMenu": "Số lượng hiển thị: _MENU_",
                        "search": "Tìm kiếm:",
                        "info": "Hiển thị _START_ đến _END_ trong _TOTAL_ đánh giá",
                        "paginate": {
                            "first": "Đầu tiên",
                            "last": "Cuối cùng",
                            "next": "Tiếp",
                            "previous": "Trước"
                        }
                    }
                });
            });
        </script>

        <style>
            .report-container {
                background-color: #ffffff; /* Màu nền trắng cho khung */
                padding: 20px;
                border-radius: 10px; /* Góc bo tròn cho khung */
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Hiệu ứng đổ bóng */
                margin-top: 30px; /* Khoảng cách phía trên khung */
            }
        </style>
    </head>
    <body>

        <!-- Include the manageBar.jsp -->
        <jsp:include page="manageBar.jsp" />

        <div class="container">
            <div class="report-container">
                <h2 class="text-center">Danh Sách Đánh Giá</h2>

                <!-- Form chọn khoảng thời gian -->
                <form action="viewRatingList" method="get" class="mb-3">
                    <label for="filter">Lọc theo:</label>
                    <select name="filter" id="filter" class="form-select w-auto d-inline-block">
                        <option value="all">Tất cả</option>
                        <option value="day">Trong ngày</option>
                        <option value="week">Trong tuần</option>
                        <option value="month">Trong tháng</option>
                    </select>
                    <button type="submit" class="btn btn-primary">Xem</button>
                </form>

                <!-- Bọc bảng đánh giá trong thẻ div -->
                <div id="ratingTableContainer">
                    <table id="ratingTable" class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>User ID</th>
                                <th>Rating</th>
                                <th>Comment</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rating" items="${ratings}">
                                <tr>
                                    <td>${rating.order_id}</td>
                                    <td>${rating.user_id}</td>
                                    <td>${rating.feedback_rating}</td>
                                    <td>${rating.feedback_comment}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>



