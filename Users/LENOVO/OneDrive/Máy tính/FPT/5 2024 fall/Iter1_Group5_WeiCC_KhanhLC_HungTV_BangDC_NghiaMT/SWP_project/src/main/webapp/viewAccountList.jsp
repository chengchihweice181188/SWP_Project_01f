<%-- 
    Document   : viewProductList
    Created on : Oct 13, 2024, 8:28:06 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <!--link để sử dụng các tính năng như tìm kiếm, phân trang, sắp xếp của dataTable-->
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.7/css/dataTables.dataTables.css" />
        <!--dataTable yêu cầu Jquery để sử dụng-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.7/js/dataTables.js"></script>
        <script>
            $(document).ready(function () {
                $('#table1').DataTable({
                    //thay đổi ngôn ngữ của dataTable
                    "language": {
                        "lengthMenu": "Số lượng hiển thị: _MENU_", // Entries per page
                        "search": "Tìm kiếm:", // Search box label
                        "info": "Hiển thị _START_ đến _END_ trong _TOTAL_ tài khoản", // Showing entries info
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
            .product-img{
                height: 110px;
                width: 120px;
            }
            .body {
                background: #fff9f0;
            }
            .btn-edit{
                width: 100%;
                height: 45px;
                margin: 5px;
            }
            .center{
                margin: 20px;
                text-align: center;
                color: red;
            }
            .no-product{
                display: flex;
                text-align: center;
                justify-content: center; /* Căn giữa theo chiều ngang */
            }
            .alert-edit{
                text-align: center;
            }
            .container-edit{
                margin-left: 280px;
                padding: 0 150px 0 150px;
                margin-right: 50px;
            }
            .btn-add{
                width: 180px;
            }
        </style>
    </head>
    <body class="body">
        <%@ include file="manageBar.jsp" %> 
        <div class="container-edit">
            <h1 class="center">Quản lí tài khoản</h1>
            <c:if test="${empty userList}">
                <h2 class="no-product">Hiện không có tài khoản nào</h2>
            </c:if> 
            <c:if test="${not empty msg}">
                <div id="failureAlert" class="alert alert-success alert-dismissible alert-edit" role="alert">
                    ${msg}
                    <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">X</button>
                </div>
                <!--Xóa biến khỏi session-->
                <c:remove var="msg" scope="session"/>
            </c:if>
            <table id="table1" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Loại</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <c:if test="${not empty userList}">
                    <tbody>
                        <c:forEach var="userVar" items="${userList}">
                            <tr>
                                <td>${userVar.user_id}</td>
                                <td>${userVar.email}</td>
                                <td>
                                    <!-- Toán tử 3 ngôi -->
                                    ${userVar.role == 2 ? 'Khách hàng' : 'Nhân viên'}
                                </td>
                                <td>
                                    <form action="ManageAccount" method="POST">
                                        <select name="txtUserStatus">
                                            <option value="0" ${userVar.user_status == 0 ? 'selected' : ''}>Mở</option>
                                            <option value="1" ${userVar.user_status == 1 ? 'selected' : ''}>Khóa</option>
                                        </select>
                                        <input type="hidden" name="txtUserId" value="${userVar.user_id}"/> <!-- id của tài khoản được chỉnh sửa -->
                                        <input type="submit" name="btnChangeStatus" value="Cập nhật" class="btn btn-primary submit-btn"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </c:if>
            </table>
        </div>
    </body>
</html>
