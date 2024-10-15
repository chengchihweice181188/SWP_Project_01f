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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

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
                response.sendRedirect("/ManageProduct");
            } else {
                if (path.equals("/ManageProduct/Add")) {
                    ViewCategoryDAO dao = new ViewCategoryDAO();
                    List<Category> categories = dao.getAllCategories();
                    if (categories == null || categories.isEmpty()) {
                        // Nếu không có category, hiển thị thông báo và không cho phép thêm sản phẩm. Ở đây dùng session vì bên dưới dùng sendRedirect
                        request.getSession().setAttribute("errorMessage", "Bạn cần tạo danh mục trước khi thêm sản phẩm");
                        //Dùng sendRedirect thì URL sẽ không thay đổi
                        response.sendRedirect("/ManageProduct");
                    } else {
                        request.getRequestDispatcher("/addProduct.jsp").forward(request, response);
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
