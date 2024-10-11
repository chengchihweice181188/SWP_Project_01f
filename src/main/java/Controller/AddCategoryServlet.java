/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import Model.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Bang
 */
public class AddCategoryServlet extends HttpServlet {

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
            out.println("<title>Servlet AddCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCategoryServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
    // Lấy giá trị từ form
    String categoryName = request.getParameter("categoryName");
    
    // Viết hoa chữ cái đầu của categoryName
    if (categoryName != null && !categoryName.isEmpty()) {
        categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
    }

    // Tạo một đối tượng Category mới
    Category category = new Category();
    category.setCategoryName(categoryName);
    category.setIsHidden(false);

    // Gọi CategoryDAO để lưu vào cơ sở dữ liệu
    CategoryDAO categoryDAO = new CategoryDAO();
    boolean success = categoryDAO.createCategory(category);

    // Sử dụng session để lưu trữ thông báo
    HttpSession session = request.getSession();
    if (!success) {
        // Nếu có lỗi, đặt thông báo lỗi vào session
        session.setAttribute("errorMessage", "Tên danh mục đã tồn tại. Vui lòng nhập tên khác.");
    } else {
        // Nếu thành công, đặt thông báo thành công vào session
        session.setAttribute("successMessage", "Thêm danh mục thành công!");
    }

    // Chuyển hướng về servlet `ListCategoriesServlet` với URL `/Category`
    response.sendRedirect(request.getContextPath() + "/Category");
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

}
