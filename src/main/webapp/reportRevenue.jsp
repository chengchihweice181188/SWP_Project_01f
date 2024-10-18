<%-- 
    Document   : reportRevenue
    Created on : Oct 18, 2024, 3:51:28 PM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Báo Cáo Lợi Nhuận</title>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+5hb7O2l5Llra6lq2I6A4KPLhY4x3A0A6PaLRjE" crossorigin="anonymous">
        <link rel="stylesheet" href="./stylesheet/reportRevenue.css"/> <!-- Custom CSS for styling -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                $('#table1').DataTable({
                    "language": {
                        "lengthMenu": "Số lượng hiển thị: _MENU_",
                        "search": "Tìm kiếm:",
                        "info": "Hiển thị _START_ đến _END_ trong _TOTAL_ orders",
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
    </head>
    <body>

        <!-- Include the manageBar.jsp -->
        <jsp:include page="manageBar.jsp" />

        <div class="container">
            <h2 class="text-center">Báo Cáo Lợi Nhuận</h2>

            <div class="text-center">
                <form method="GET" action="ReportRevenue" class="form-inline">
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label for="startDate">Từ Ngày: </label>
                            <input type="date" name="startDate" id="startDate" class="form-control" />
                        </div>
                        <div class="col-md-4">
                            <label for="endDate">Đến Ngày: </label>
                            <input type="date" name="endDate" id="endDate" class="form-control" />
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">Lọc theo Ngày</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="text-center">
                <a href="ReportRevenue?period=day" class="btn btn-primary">Xem theo Ngày</a>
                <a href="ReportRevenue?period=week" class="btn btn-primary">Xem theo Tuần</a>
                <a href="ReportRevenue?period=month" class="btn btn-primary">Xem theo Tháng</a>
            </div>

            <table id="table1" class="table table-striped table-bordered mt-4">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Ghi Chú Order</th>
                        <th>Giá Tiền</th>
                        <th>Ngày Đặt Hàng</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${revenues}">
                    <tr>
                        <td>${order.order_id}</td>
                        <td>${order.order_note}</td>
                        <td>${order.order_price}</td>
                        <td>${order.order_date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <h3 class="text-right mt-4">Tổng Lợi Nhuận:
                <c:if test="${not empty totalRevenue}">
                    ${totalRevenue}
                </c:if>
            </h3>
        </div>
    </body>
</html>