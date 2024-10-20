<%-- 
    Document   : editProduct
    Created on : Oct 13, 2024, 8:28:36 PM
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
            margin: 10px 0 -15px -40px;
            color: red;
        }
        .txt-box{
            width: 650px;
        }
        .form-edit{
            font-size: 26px;
            display: flex;
            margin-top: 50px;
        }
        .row-edit {
            display: flex;
            align-items: center;
            width: 800px;
            margin: 0px 0px 50px -300px;
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
        .h4-edit{
            color: red;
        }
        .product-img{
            height: 110px;
            width: 120px;
        }
    </style>
    <body class="body">
        <%@ include file="manageBar.jsp" %> 
        <div class="container-edit">
            <h1 class="title">Sửa sản phẩm</h1>
            <div class="form-edit">
                <form method="post" action="ManageProduct" enctype="multipart/form-data">
                    <c:if test="${not empty nameError}">
                        <h4 class="h4-edit">${nameError}</h4>
                        <c:set var="nameError" value="" scope="session"/>
                    </c:if>
                    <div class="row-edit">
                        <label for="txtProName">Tên: &nbsp;</label>
                        <input type="text" name="txtProName" class="txt-box" value="${product.product_name}" required/>
                    </div>
                    <c:if test="${not empty desError}">
                        <h4 class="h4-edit">${desError}</h4>
                        <c:set var="desError" value="" scope="session"/>
                    </c:if>
                    <div class="row-edit">
                        <label for="txtProDes">Mô tả: &nbsp;</label>
                        <textarea name="txtProDes" class="txt-box" required>${product.product_description}</textarea>
                    </div>
                    <c:if test="${not empty priceError}">
                        <h4 class="h4-edit">${priceError}</h4>
                        <c:set var="priceError" value="" scope="session"/>
                    </c:if>
                    <div class="row-edit">
                        <label for="txtProPrice">Giá (đ): &nbsp;</label>
                        <input type="number" name="txtProPrice" class="txt-box" step="0.001" value="${product.product_price}" required/>
                    </div>
                    <c:if test="${not empty fileError}">
                        <h4 class="h4-edit">${fileError}</h4>
                        <c:set var="fileError" value="" scope="session"/>
                    </c:if>
                    <div class="row-edit">
                        <label for="txtProImg">Hình ảnh&nbsp;<br>(sử dụng ảnh vuông)&nbsp;</label>
                        <img src="/ProductImg/${product.product_image}" alt="Product Image" class="product-img"/>&nbsp;
                        <input type="file" name="txtProImg" class="txt-box" accept="image/*"/>
                    </div>
                    <div class="row-edit">
                        <label for="txtCatId">Danh mục: &nbsp;</label>
                        <select name="txtCatId" class="txt-box" required>
                            <c:forEach var="catVar" items="${catList}">
                                <!-- Kiểm tra nếu sản phẩm thuộc danh mục nào -->
                                <option value="${catVar.category_id}" ${catVar.category_id == product.category_id ? 'selected="selected"' : ''}>${catVar.category_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="hidden" name="txtProId" value="${product.product_id}"/> <!-- id của sản phẩm đang được chỉnh sửa -->
                    <input type="submit" name="btnUpdateProduct" value="Sửa" class="btn btn-primary submit-btn"/>
                </form>
            </div>
        </div>
    </body>
</html>
