<%-- 
    Document   : index
    Created on : Oct 10, 2024, 11:32:32 AM
    Author     : Bang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">        
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/category.css">
        <title>Quản lý danh mục</title>
        <script>
            function openPopup(popupId) {
            document.querySelector(.overlay[data - id = "${popupId}"]).style.display = 'flex';
            }

            function closePopup(popupId) {
            document.querySelector(.overlay[data - id = "${popupId}"]).style.display = 'none';
            }
            // Hàm hiển thị popup lỗi
            function showError(message) {
            alert(message); // Sử dụng alert hoặc dùng popup tùy ý
            }

            // Hàm hiển thị popup thành công
            function showSuccess(message) {
            alert(message);
            }
        </script>
    </head>
    <body>
        <div class="container">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="logo">
                    <img src="food_logo.png" alt="Food Logo">
                    <h2>FOOD FAST FOOD</h2>
                </div>
                <ul class="menu-list">
                    <li>Quản lý danh mục</li>
                    <li>Quản lý sản phẩm</li>
                    <li>Quản lý lựa chọn</li>
                    <li><a href="${pageContext.request.contextPath}/Promotion">Quản lý khuyến mãi</a></li>
                    <li>Thống kê doanh thu</li>
                    <li>Thống kê sản phẩm</li>
                    <li>Xem danh sách đánh giá</li>
                </ul>
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <div class="header">
                    <h1>Quản lí danh mục</h1>
                    <button onclick="openCreatePopup()" class="btn-add">Thêm</button>

                </div>
                <div class="search-bar">
                    <input type="text" placeholder="Tìm kiếm">
                    <button class="btn-search">Tìm kiếm</button>
                </div>
                <c:if test="${empty categories}">
                    <p>Không có danh mục nào.</p>
                </c:if>

                <div class="category-list">
                    <c:forEach var="category" items="${categories}">
                        <div class="category-item">
                            <span>${category.categoryName}</span>
                            <button class="btn-edit" onclick="openEditPopup(${category.categoryId}, '<c:out value="${category.categoryName}" />')">Sửa</button>
                            <button class="btn-delete" onclick="openPopup(${category.categoryId}, '${category.categoryName}')">Xóa</button>
                        </div>
                    </c:forEach>                
                </div>
            </div>
        </div>
        <jsp:include page="categoryCreate.jsp"></jsp:include>       
        <jsp:include page="categoryDelete.jsp"></jsp:include>
        <jsp:include page="categoryEdit.jsp"></jsp:include>
        <c:if test="${not empty sessionScope.errorMessage}">
            <script>
                showError("${sessionScope.errorMessage}");
            </script>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.successMessage}">
            <script>
                showSuccess("${sessionScope.successMessage}");
            </script>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

    </body>
</html>