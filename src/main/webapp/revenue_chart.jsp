<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monthly Revenue Table</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
      <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #fff9f0;
        }

        /* Sidebar Styles */
        .sidebar {
            background-color:orange;
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

        /* Content Styles */
        .container {
            margin-left: 250px; /* Đảm bảo nội dung không đè lên sidebar */
            padding: 20px;
        }

        h1.text-center {
            font-size: 2.5rem;
            color: #343a40;
            margin-bottom: 20px;
            font-weight: 700;
        }

        /* Bảng hiển thị */
        .table {
            margin-top: 30px;
            background-color: white;
            border-radius: 5px;
            overflow: hidden;
        }

        .table th, .table td {
            text-align: center;
            vertical-align: middle;
        }

        .table thead th {
            background-color: #343a40;
            color: white;
            font-size: 1.2rem;
        }

        .table tbody tr:nth-child(odd) {
            background-color: #f8f9fa;
        }

        .table tbody tr:nth-child(even) {
            background-color: #e9ecef;
        }

        /* Mũi tên chuyển năm */
        .text-center {
            margin-bottom: 30px;
        }

        .btn-outline-primary {
            border-radius: 5px;
            padding: 10px 20px;
            font-size: 16px;
        }

        .text-center span.mx-3 {
            font-size: 1.2rem;
            font-weight: bold;
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
                    <li><a href="${pageContext.request.contextPath}/BestSeller">Thống kê sản phẩm</a></li>
                    <li><a href="${pageContext.request.contextPath}/OrderFeedback">Xem danh sách đánh giá</a></li>
                    <li><a href="${pageContext.request.contextPath}/Account">Quản lí tài khoản</a></li>
                </ul>
                <button class="btn btn-danger">Logout</button>
            </div>
        <h1 class="text-center">Doanh thu hằng tháng</h1>

        <!-- Phần hiển thị mũi tên chuyển năm -->
        <div class="text-center mb-4">
            <form action="/Revenue" method="GET" class="d-inline">
                <input type="hidden" name="year" value="${currentYear - 1}">
                <button type="submit" class="btn btn-outline-primary">
                    <span>&larr; Năm trước</span>
                </button>
            </form>

            <span class="mx-3">Năm: ${currentYear}</span>

            <form action="/Revenue" method="GET" class="d-inline">
                <input type="hidden" name="year" value="${currentYear + 1}">
                <button type="submit" class="btn btn-outline-primary">
                    <span>Năm sau &rarr;</span>
                </button>
            </form>
        </div>

        <!-- Bảng hiển thị doanh thu -->
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>Tháng/Năm</th>
                    <th>Tổng Doanh Thu (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="revenue" items="${revenueList}">
                    <tr>
                        <td>${revenue.month}/${revenue.year}</td>
                        <td>${revenue.totalRevenue}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty revenueList}">
                    <tr>
                        <td colspan="2" class="text-center">Không có dữ liệu cho năm ${currentYear}</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
