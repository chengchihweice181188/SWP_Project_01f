/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderManagementDAO;
import Models.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class OrderManagementController extends HttpServlet {

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
            out.println("<title>Servlet OrderManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderManagementController at " + request.getContextPath() + "</h1>");
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
        OrderManagementDAO orderDAO = new OrderManagementDAO();
        List<Order> orderList = null;
        String orderStatus = request.getParameter("order_status");

        // Nếu không có trạng thái đơn hàng nào được chọn, mặc định lấy tất cả
        if (orderStatus == null) {
            orderStatus = "all";
        }

        try {
            // Gọi phương thức DAO để lấy danh sách đơn hàng theo trạng thái
            orderList = orderDAO.getOrdersByStatus(orderStatus);
            // Đặt danh sách đơn hàng và trạng thái vào request
            request.setAttribute("orderList", orderList);
            request.setAttribute("order_status", orderStatus);
        } catch (SQLException ex) {
            // Ghi log chi tiết cho SQLException
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, "Lỗi cơ sở dữ liệu: ", ex);

            // Đặt thông báo lỗi vào request để hiển thị trên trang JSP
            request.setAttribute("error", "Lỗi kết nối cơ sở dữ liệu. Vui lòng thử lại sau!");
        } catch (Exception ex) {
            // Ghi log chi tiết cho các lỗi khác
            Logger.getLogger(OrderManagementController.class.getName()).log(Level.SEVERE, "Lỗi hệ thống: ", ex);

            // Đặt thông báo lỗi vào request để hiển thị trên trang JSP
            request.setAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
        }

        // Chuyển tiếp tới trang JSP bất kể có lỗi hay không
        request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
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
