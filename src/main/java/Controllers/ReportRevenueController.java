/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ReportRevenueDAO;
import Models.Order;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author tvhun
 */
@WebServlet(name = "ReportRevenueController", urlPatterns = {"/ReportRevenue"})
public class ReportRevenueController extends HttpServlet {

    private ReportRevenueDAO reportRevenueDAO = new ReportRevenueDAO();

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
            out.println("<title>Servlet ReportRevenueController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportRevenueController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String period = request.getParameter("period"); // Lấy thông tin thời gian (day, week, month)
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        List<Order> revenues = null;
        double totalRevenue = 0;

        try {
            if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
                revenues = reportRevenueDAO.getRevenueByDateRange(startDate, endDate);
            } else if ("day".equals(period)) {
                revenues = reportRevenueDAO.getRevenueByDay();
            } else if ("week".equals(period)) {
                revenues = reportRevenueDAO.getRevenueByWeek();
            } else if ("month".equals(period)) {
                revenues = reportRevenueDAO.getRevenueByMonth();
            }

            if (revenues != null) {
                for (Order order : revenues) {
                    totalRevenue += order.getOrder_price();
                }
            }

            request.setAttribute("revenues", revenues); // Đưa danh sách lợi nhuận vào request
            request.setAttribute("totalRevenue", totalRevenue); // Đưa tổng lợi nhuận vào request
            request.getRequestDispatcher("/reportRevenue.jsp").forward(request, response); // Chuyển hướng tới JSP

        } catch (SQLException e) {
            e.printStackTrace();
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
