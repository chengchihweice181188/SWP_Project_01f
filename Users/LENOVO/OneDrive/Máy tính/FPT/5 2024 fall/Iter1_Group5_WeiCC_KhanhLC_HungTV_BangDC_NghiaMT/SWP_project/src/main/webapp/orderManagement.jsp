<%-- 
    Document   : orderManagement
    Created on : Oct 10, 2024, 8:25:44 PM
    Author     : Luu Chi Khanh-CE181175
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý đơn hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="./assets/stylesheet/manageBar.css"/>
        <link rel="stylesheet" href="./assets/stylesheet/orderManagement.css"/>   
    </head>
    <body>  
        <%@ include file="manageBar.jsp" %> 
        <div class="table-container">
            <h2>Quản lý đơn hàng</h2>
            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>
            <!-- Form lọc trạng thái đơn hàng -->
            <form method="GET" action="OrderManagement">
                <div class="form-group">
                    <label for="order_status_filter">Lọc theo trạng thái đơn hàng:</label>
                    <select id="order_status_filter" name="order_status" class="form-select">
                        <option value="all">Tất cả</option>
                        <option value="Chưa xử lý" ${param.order_status == 'Chưa xử lý' ? 'selected' : ''}>Chưa xử lý</option>
                        <option value="Đã xử lý" ${param.order_status == 'Đã xử lý' ? 'selected' : ''}>Đã xử lý</option>
                        <option value="Đang giao" ${param.order_status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                        <option value="Đã giao" ${param.order_status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                        <option value="Đã hủy" ${param.order_status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </div>
            </form>
            <table class="table table-striped">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Tên người dùng</th>
                        <th>Ghi chú</th>
                        <th>Ngày đặt hàng</th>
                        <th>Trạng thái đơn hàng</th>
                        <th>Cập nhật trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty orderList}">
                            <!-- Khi không có dữ liệu, hiển thị hàng trống -->
                            <tr>
                                <td colspan="6" class="text-center">Không có đơn hàng nào</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="ord" items="${orderList}">
                                <tr>
                                    <td>${ord.order_id}</td> <!-- ID Đơn Hàng -->
                                    <td>${ord.username}</td> <!-- Tên Người Dùng -->
                                    <td>${ord.order_note}</td> <!-- Ghi Chú -->
                                    <td>${ord.order_date}</td> <!-- Ngày Đặt Hàng -->
                                    <td>${ord.order_status}</td> <!-- Trạng Thái Hiện Tại -->
                                    <td>
                                        <form action="UpdateOrder" method="POST" class="d-flex justify-content-between" style="align-items: center;">
                                            <input type="hidden" name="order_id" value="${ord.order_id}">
                                            <!-- Dropdown cập nhật trạng thái -->
                                            <select class="form-select" name="order_status">
                                                <option value="Chưa xử lý" ${ord.order_status == 'Chưa xử lý' ? 'selected' : ''}>Chưa xử lý</option>
                                                <option value="Đã xử lý" ${ord.order_status == 'Đã xử lý' ? 'selected' : ''}>Đã xử lý</option>
                                                <option value="Đang giao" ${ord.order_status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                                                <option value="Đã giao" ${ord.order_status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                                                <option value="Đã hủy" ${ord.order_status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                                            </select>
                                            <c:choose>
                                                <c:when test="${ord.order_status != 'Đã giao' && ord.order_status != 'Đã hủy'}">
                                                    <button type="submit" class="btn btn-primary">Cập Nhật</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Không hiển thị nút Cập Nhật khi đơn hàng đã giao -->
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <script>
            function confirmLogout(event) {
                const confirmation = confirm("Bạn có chắc chắn muốn đăng xuất không?");
                if (!confirmation) {
                    event.preventDefault(); // Ngăn chặn form nếu người dùng chọn "Cancel"
                }
            }
        </script>
    </body>
</html>
