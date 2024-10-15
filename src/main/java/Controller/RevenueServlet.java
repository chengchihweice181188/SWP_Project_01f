/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.RevenueDAO;
import Model.Revenue;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Bang
 */
public class RevenueServlet extends HttpServlet {

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
            out.println("<title>Servlet RevenueServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RevenueServlet at " + request.getContextPath() + "</h1>");
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
        // Lấy tham số year từ yêu cầu
        String yearParam = request.getParameter("year");
        int currentYear;
        
        if (yearParam != null) {
            currentYear = Integer.parseInt(yearParam); // Chuyển đổi thành số nguyên
        } else {
            // Nếu không có năm nào được gửi lên, mặc định là năm hiện tại
            currentYear =LocalDate.now().getYear(); // Hoặc bạn có thể lấy năm hiện tại bằng LocalDate.now().getYear()
        }

        // Gọi DAO để lấy danh sách doanh thu theo năm
        RevenueDAO revenueDAO = new RevenueDAO();
        List<Revenue> revenueList = revenueDAO.getMonthlyRevenueByYear(currentYear);

        // Đưa danh sách revenue vào request attribute để hiển thị trên JSP
        request.setAttribute("revenueList", revenueList);
        request.setAttribute("currentYear", currentYear); // Truyền năm hiện tại vào JSP

        // Chuyển hướng đến trang JSP hiển thị bảng doanh thu
        RequestDispatcher dispatcher = request.getRequestDispatcher("revenue_chart.jsp");
        dispatcher.forward(request, response);
    
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
