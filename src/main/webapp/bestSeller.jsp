<%@page import="java.util.Map"%>
<%@page import="Model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản phẩm bán chạy nhất</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Cấu trúc tổng quan */
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
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

            /* Nội dung chính */
            .container {
                margin-left: 250px;
                padding: 20px;
            }

            h1 {
                font-size: 2.5rem;
                color: #343a40;
                font-weight: bold;
                text-align: center;
                margin-top: 20px;
                margin-bottom: 40px;
            }

            /* Bảng sản phẩm bán chạy nhất */
            table {
                width: 100%;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }

            table th, table td {
                text-align: center;
                padding: 12px 15px;
            }

            table thead th {
                background-color: #343a40;
                color: white;
                font-size: 1.2rem;
            }

            table tbody tr:nth-child(odd) {
                background-color: #f8f9fa;
            }

            table tbody tr:nth-child(even) {
                background-color: #e9ecef;
            }

            /* Nút điều hướng năm */
            .year-navigation {
                display: flex;
                justify-content: center;
                margin-bottom: 30px;
            }

            .year-navigation form {
                margin: 0 15px;
            }

            .year-navigation button {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                cursor: pointer;
            }

            .year-navigation button:hover {
                background-color: #0056b3;
            }

            /* Căn chỉnh cho mobile */
            @media (max-width: 768px) {
                .container {
                    margin-left: 0;
                    padding: 10px;
                }

                .sidebar {
                    width: 100%;
                    height: auto;
                    position: relative;
                }

                .sidebar button {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>
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

        <div class="container">
            <h1>Sản phẩm bán chạy nhất trong năm ${year}</h1>

            <!-- Nút chuyển năm -->
            <div class="year-navigation">
                <form method="get" action="/BestSeller">
                    <input type="hidden" name="year" value="${year-1}"/>
                    <button type="submit">Năm trước</button>
                </form>
                <span class="mx-3">Năm: ${year}</span>
                <form method="get" action="/BestSeller">
                    <input type="hidden" name="year" value="${year+1}"/>
                    <button type="submit">Năm sau</button>
                </form>
            </div>

            <!-- Bảng hiển thị best seller của từng tháng -->
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Tháng</th>
                        <th>Sản phẩm bán chạy nhất</th>
                        <th>Giá</th>
                        <th>Số lượng bán</th>
                    </tr> 
                </thead>
                <tbody>
                    <%
                        Integer currentMonthAttr = (Integer) request.getAttribute("currentMonth");
                        if (currentMonthAttr != null) {
                            int currentMonth = currentMonthAttr;
                            Map<Integer, Product> bestSellers = (Map<Integer, Product>) request.getAttribute("bestSeller");

                            if (bestSellers != null) {
                                for (int i = 1; i <= currentMonth; i++) {
                                    Product product = bestSellers.get(i);
                    %>
                    <tr>
                        <td><%= i %></td>
                        <td><%= product != null ? product.getProductName() : "No data" %></td>
                        <td><%= product != null ? product.getProductPrice() : "N/A" %></td>
                        <td><%= product != null ? product.getTotalQuantity() : "N/A" %></td>
                    </tr>
                    <%
                                }
                            } else {
                                out.println("<tr><td colspan='4'>Không có dữ liệu về sản phẩm bán chạy.</td></tr>");
                            }
                        } else {
                            out.println("<tr><td colspan='4'>Không có dữ liệu cho tháng hiện tại.</td></tr>");
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
