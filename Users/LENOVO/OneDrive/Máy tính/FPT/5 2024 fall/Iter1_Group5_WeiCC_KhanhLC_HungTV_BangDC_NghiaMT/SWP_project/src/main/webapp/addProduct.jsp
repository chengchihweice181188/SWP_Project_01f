<%-- 
    Document   : addProduct
    Created on : Oct 17, 2024, 6:02:43 PM
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
        <link rel="stylesheet" href="https://cdn.datatables.net/2.0.7/css/dataTables.dataTables.css" />
    </head>
    <style>
        .body{
            background: #fff9f0;
        }
        .container-edit{
            margin: 25px 50px 0px 300px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .title{
            margin: 10px 0 -15px -30px;
            color: red;
        }
        .txt-box{
            width: 650px;
        }
        .form-edit{
            font-size: 26px;
            display: flex;
        }
        .row-edit {
            display: flex;
            align-items: center;
            width: 800px;
            margin: 50px 0px 50px -300px;
        }
        .row-edit label {
            flex: 0 0 250px; /* Đặt chiều rộng cố định cho label */
            text-align: right; /* Căn phải nội dung trong label */
            margin-right: 10px;
        }

        .row-edit input {
            flex: 1; /* Đặt input chiếm phần còn lại của hàng */
        }
        .submit-btn{
            margin-left: -40px;
            width: 150px;
        }
    </style>
    <body class="body">
        <%@ include file="manageBar.jsp" %> 
        <div class="container-edit">
            <h1 class="title">Thêm sản phẩm</h1>
            <div class="form-edit">
                <form method="post" action="ManageProduct" enctype="multipart/form-data">
                    <div class="row-edit">
                        <label for="txtProName">Tên&nbsp;</label>
                        <input type="text" name="txtProName" class="txt-box" required/>
                    </div>
                    <div class="row-edit">
                        <label for="txtProDes">Mô tả&nbsp;</label>
                        <textarea name="txtProDes" class="txt-box" required></textarea>
                    </div>
                    <div class="row-edit">
                        <label for="txtProPrice">Giá (đ)&nbsp;</label>
                        <input type="number" name="txtProPrice" class="txt-box" step="0.001" required/>
                    </div>
                    <div class="row-edit">
                        <label for="txtProImg">Hình ảnh&nbsp;<br>(sử dụng ảnh vuông)&nbsp;</label>
                        <input type="file" name="txtProImg" class="txt-box" accept="image/*" required/>
                    </div>
                    <div class="row-edit">
                        <label for="txtCatId">Danh mục&nbsp;</label>
                        <select name="txtCatId" class="txt-box" required>
                            <c:forEach var="catVar" items="${catList}">
                                <option value="${catVar.category_id}">${catVar.category_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" name="btnAddProduct" value="Tạo" class="btn btn-success submit-btn"/>
                </form>
            </div>
        </div>
    </body>
</html>
