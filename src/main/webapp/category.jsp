<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">        
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý danh mục</title>
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #fff9f0;
            }

            .sidebar {
                width: 250px;
                background-color: #f4a261;
                height: 100vh;
                position: fixed;
                left: 0;
                top: 0;
                padding: 20px;
                box-sizing: border-box;
            }

            .logo {
                text-align: center;
                margin-bottom: 20px;
            }

            .logo img {
                width: 80%;
                height: auto;
            }

            .sidebar ul {
                list-style-type: disc;
                padding-left: 20px;
                margin-top: 20px;
            }

            .sidebar ul li {
                margin-bottom: 15px;
            }

            .sidebar ul li a {
                text-decoration: none;
                color: #333;
                font-size: 18px;
                display: block;
            }

            .sidebar ul li a:hover {
                color: #264653;
            }

            .btn-danger {
                background-color: #e76f51;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                width: 100%;
                text-align: center;
                margin-bottom: 20px;
                border-radius: 5px;
            }

            .main-content {
                margin-left: 250px;
                padding: 20px;
            }

            @media screen and (max-width: 768px) {
                .sidebar {
                    width: 180px;
                }
                .main-content {
                    margin-left: 200px;
                }
            }

            @media screen and (max-width: 576px) {
                .sidebar {
                    position: relative;
                    width: 100%;
                    height: auto;
                }
                .main-content {
                    margin-left: 0;
                }
            }

            .header {
                text-align: center;
                margin-bottom: 20px;
            }

            .header h1 {
                font-size: 36px;
                font-weight: bold;
                color: #333;
                margin: 0;
            }

            .header-actions {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 10px;
                gap: 20px;
            }

            .btn {
                padding: 5px 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }

            .btn-add {
                background-color: #6c757d;
                color: white;
                padding: 8px 16px;
                font-size: 14px;
                border-radius: 5px;
                cursor: pointer;
            }

            .search-bar {
                display: flex;
                justify-content: flex-end;
                width: 300px;
            }

            .search-bar input {
                padding: 8px;
                width: 100%;
                border-radius: 5px;
                border: 1px solid #ccc;
                font-size: 14px;
            }

            .btn-search {
                background-color: #ff6600;
                color: white;
            }

            .category-list {
                background-color: #f9f9f9;
                border-radius: 5px;
                padding: 20px;
            }

            .category-header,
            .category-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 0;
                border-bottom: 1px solid #ccc;
            }

            .category-header {
                font-weight: bold;
                border-bottom: 2px solid #ccc;
                margin-bottom: 15px;
            }

            .category-actions {
                display: flex;
                gap: 10px;
            }

            .btn-edit {
                background-color: #2196F3;
                color: white;
                padding: 5px 10px;
                border-radius: 5px;
            }

            .btn-delete {
                background-color: #f44336;
                padding: 5px 10px;
                border-radius: 5px;
            }

            .header-name,
            .header-actions {
                font-weight: bold;
            }

        </style>
        <script>
            function openPopup(popupId) {
                document.querySelector('.overlay[data-id="' + popupId + '"]').style.display = 'flex';
            }

            function closePopup(popupId) {
                document.querySelector('.overlay[data-id="' + popupId + '"]').style.display = 'none';
            }

            function showError(message) {
                alert(message);
            }

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

            <!-- Main Content -->
            <div class="main-content">
                <div class="header">
                    <h1>Quản lí danh mục</h1>
                </div>
                <div class="header-actions">
                    <form method="get" action="" class="search-bar">
                        <input type="text" name="searchQuery" placeholder="Tìm kiếm..." value="${param.searchQuery != null ? param.searchQuery : ''}">
                        <button class="btn-search" type="submit">Tìm kiếm</button>
                    </form>
                    <button onclick="openCreatePopup()" class="btn-add">Thêm</button>
                </div>

                <div class="category-list">
                    <div class="category-header">
                        <span class="header-name">Tên danh mục</span>
                        <span class="header-actions">Hành động</span>
                    </div>
                    <c:if test="${empty categories}">
                        <p>Không có danh mục nào.</p>
                    </c:if>

                    <c:forEach var="category" items="${categories}">
                        <div class="category-item">
                            <span class="category-name">${category.categoryName}</span>
                            <div class="category-actions">
                                <button class="btn-edit" onclick="openEditPopup(${category.categoryId}, '<c:out value="${category.categoryName}" />')">Sửa</button>
                                <button class="btn-delete" onclick="openPopup(${category.categoryId}, '${category.categoryName}')">Xóa</button>
                            </div>
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
