<%-- 
    Document   : reportProduct
    Created on : Oct 18, 2024, 3:51:17 PM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Báo Cáo Sản Phẩm</title>
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.7/css/dataTables.dataTables.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="./stylesheet/reportProduct.css"/> <!-- Custom CSS for styling -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.7/js/dataTables.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script>
            $(document).ready(function () {
                $('#table1').DataTable({
                    "language": {
                        "lengthMenu": "Số lượng hiển thị: _MENU_",
                        "search": "Tìm kiếm:",
                        "info": "Hiển thị _START_ đến _END_ trong _TOTAL_ sản phẩm",
                        "paginate": {
                            "first": "Đầu tiên",
                            "last": "Cuối cùng",
                            "next": "Tiếp",
                            "previous": "Trước"
                        }
                    },
                    "columnDefs": [
                        {"defaultContent": "-", "targets": "_all"}
                    ]
                });
            });
        </script>
    </head>
    <body>
        <!-- Include the manageBar.jsp -->
        <jsp:include page="manageBar.jsp" />

        <!-- Form để submit yêu cầu báo cáo -->
        <div class="container">
            <h2 class="text-center">Báo Cáo Sản Phẩm</h2>
            <form action="productReport" method="get" class="mb-3">
                <div class="form-group">
                    <label for="reportType">Loại báo cáo:</label>
                    <select id="reportType" name="reportType" class="form-control">
                        <option value="all">Tất cả sản phẩm</option>
                        <option value="weekly">Báo cáo tuần</option>
                        <option value="monthly">Báo cáo tháng</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary mt-2">Xem Báo Cáo</button>
            </form>

            <!-- Bảng kết quả báo cáo sản phẩm -->
            <table id="table1" class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID Sản Phẩm</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Giá Tiền</th>
                        <th>Số Lượng Bán</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="productReport" items="${productReports}">
                        <tr>
                            <td>${productReport[0] != null ? productReport[0] : '-'}</td> <!-- Product ID -->
                            <td>${productReport[1] != null ? productReport[1] : '-'}</td> <!-- Product Name -->
                            <td>${productReport[2] != null ? productReport[2] : '0.00'}</td> <!-- Product Price -->
                            <td>${productReport[3] != null ? productReport[3] : '0'}</td> <!-- Total Quantity -->
                        </tr>
                    </c:forEach>
                    <c:if test="${empty productReports}">
                        <tr>
                            <td colspan="4" class="text-center">Không có dữ liệu để hiển thị</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </body>
</html>

