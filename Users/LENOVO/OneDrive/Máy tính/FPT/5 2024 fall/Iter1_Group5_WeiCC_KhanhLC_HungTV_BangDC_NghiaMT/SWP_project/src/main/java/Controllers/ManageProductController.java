/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ManageProductDAO;
import DAOs.ViewCategoryDAO;
import Models.Category;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 15 // 15MB
)
/**
 *
 * @author LENOVO
 */
public class ManageProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/ManageProduct")) {
            ManageProductDAO dao = new ManageProductDAO();
            //Đưa product list tạo ở DAO vào session
            List<Product> productList = dao.getAllProduct();
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("/viewProductList.jsp").forward(request, response);
        } else {
            if (path.startsWith("/ManageProduct/Delete/")) {
                String[] s = path.split("/");
                String id = s[s.length - 1];
                ManageProductDAO dao = new ManageProductDAO();
                dao.deleteProduct(id);
                dao.deleteProductOptions(id);
                response.sendRedirect("/ManageProduct");
            } else {
                if (path.equals("/ManageProduct/Add")) {
                    ViewCategoryDAO dao = new ViewCategoryDAO();
                    List<Category> categories = dao.getAllCategories();
                    if (categories == null || categories.isEmpty()) {
                        // Nếu không có category, hiển thị thông báo và không cho phép thêm sản phẩm. Ở đây dùng session vì bên dưới dùng sendRedirect
                        request.getSession().setAttribute("catError", "Bạn cần tạo danh mục trước khi thêm sản phẩm");
                        //Dùng sendRedirect thì URL sẽ không thay đổi
                        response.sendRedirect("/ManageProduct");
                    } else {
                        request.setAttribute("catList", categories);
                        request.getRequestDispatcher("/addProduct.jsp").forward(request, response);
                    }
                } else {
                    if (path.startsWith("/ManageProduct/Edit/")) {
                        String[] s = path.split("/");
                        String id = s[s.length - 1];
                        ManageProductDAO daoManagePro = new ManageProductDAO();
                        Product obj = daoManagePro.getProductById(id);
                        ViewCategoryDAO daoViewCat = new ViewCategoryDAO();
                        List<Category> categories = daoViewCat.getAllCategories();
                        if (obj == null) {
                            response.sendRedirect("/ManageProduct");
                        } else {
                            request.setAttribute("catList", categories);
                            request.setAttribute("product", obj);
                            request.getRequestDispatcher("/editProduct.jsp").forward(request, response);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("btnAddProduct") != null) {
            String productName = request.getParameter("txtProName");
            String productDescription = request.getParameter("txtProDes");
            double productPrice = Double.parseDouble(request.getParameter("txtProPrice"));
            Part cover = request.getPart("txtProImg"); // For file upload, make sure your form enctype is multipart/form-data
            int categoryId = Integer.parseInt(request.getParameter("txtCatId"));
            String coverPath = handleFileUpload(cover);
            Product obj = new Product(productName, productDescription, productPrice, coverPath, categoryId);
            ManageProductDAO dao = new ManageProductDAO();
            int count = dao.addProduct(obj);
            if (count > 0) {
                response.sendRedirect("/ManageProduct");
            } else {
                response.sendRedirect("/ManageProduct/Add");
            }
        }
        if (request.getParameter("btnUpdateProduct") != null) {
            int productId = Integer.parseInt(request.getParameter("txtProId"));
            String productName = request.getParameter("txtProName");
            String productDescription = request.getParameter("txtProDes");
            double productPrice = Double.parseDouble(request.getParameter("txtProPrice"));
            int categoryId = Integer.parseInt(request.getParameter("txtCatId"));
            ManageProductDAO dao = new ManageProductDAO();
            int count;
            Part cover = request.getPart("txtProImg"); // For file upload, make sure your form enctype is multipart/form-data
            // Kiểm tra nếu người dùng có chọn ảnh mới hay không. Kiểu Part luôn khác null nên so sánh kích thước
            if (cover != null && cover.getSize() > 0) {
                String coverPath = handleFileUpload(cover);
                Product obj = new Product(productId, productName, productDescription, productPrice, coverPath, categoryId);
                count = dao.updateProduct(obj);
            } else {
                // Giữ nguyên ảnh cũ nếu không có ảnh mới
                Product obj = new Product(productId, productName, productDescription, productPrice, categoryId);
                count = dao.updateProductWithoutImg(obj);
            }
            if (count > 0) {
                response.sendRedirect("/ManageProduct");
            } else {
                response.sendRedirect("/ManageProduct/Edit/" + productId);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleFileUpload(Part cover) throws IOException {
        String fileName = Paths.get(cover.getSubmittedFileName()).getFileName().toString();
        String uploadDir = "ProductImg";
        Path uploadPath = Paths.get(getServletContext().getRealPath(""), uploadDir);
        // Generate a unique file name to prevent overwriting
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        // Save the uploaded file to the server
        try ( InputStream inputStream = cover.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        // Return the relative path to the uploaded file
        return uniqueFileName;
    }
}
