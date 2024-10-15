/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductDAO;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author Bang
 */
public class BestSellerServlet extends HttpServlet {

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
            out.println("<title>Servlet BestSellerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BestSellerController at " + request.getContextPath() + "</h1>");
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
        // Lấy năm từ tham số yêu cầu, nếu không có thì mặc định là năm hiện tại
        String yearParam = request.getParameter("year");
        int selectedYear;
        if (yearParam != null) {
            selectedYear = Integer.parseInt(yearParam);
        } else {
            selectedYear = LocalDate.now().getYear(); // Mặc định là năm hiện tại nếu không có tham số
        }

        // Lấy tháng hiện tại
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Kiểm tra xem năm đang được yêu cầu có phải là năm hiện tại không
        int displayMonth = selectedYear == currentYear ? currentMonth-1 : 12;

        // Gọi DAO để lấy danh sách best seller theo từng tháng đã kết thúc
        ProductDAO productDAO = new ProductDAO();
        try {
            Map<Integer, Product> bestSeller = productDAO.getBestSellersByYear(selectedYear, displayMonth);
            request.setAttribute("bestSeller", bestSeller);
            request.setAttribute("year", selectedYear);  // Truyền giá trị year xuống JSP
            request.setAttribute("currentMonth", displayMonth);  // Chỉ truyền tháng cần hiển thị

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/bestSeller.jsp").forward(request, response);
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
        processRequest(request, response);
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
