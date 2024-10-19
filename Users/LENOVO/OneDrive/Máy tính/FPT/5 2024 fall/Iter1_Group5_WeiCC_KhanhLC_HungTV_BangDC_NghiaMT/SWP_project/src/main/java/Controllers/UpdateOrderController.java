/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderManagementDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class UpdateOrderController extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderController at " + request.getContextPath() + "</h1>");
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
        // Lấy giá trị từ request
        String orderIdStr = request.getParameter("order_id");
        String selectedStatus = request.getParameter("order_status");
        // Nếu orderId hoặc selectedStatus null, in ra lỗi và ngừng xử lý
        if (orderIdStr == null || selectedStatus == null) {
            System.out.println("Order ID hoặc Order Status bị null");
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
            return;  // Kết thúc hàm nếu không có dữ liệu hợp lệ
        }
        // Kiểm tra xem order_id có phải là số hợp lệ không
        int order_id;
        try {
            order_id = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Order ID không phải là số hợp lệ: " + orderIdStr);
            request.setAttribute("error", "Order ID không hợp lệ.");
            request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
            return;
        }
        // Kiểm tra xem selectedStatus có nằm trong danh sách trạng thái hợp lệ không
        String[] validStatuses = {"Chưa xử lý", "Đã xử lý", "Đang giao", "Đã giao", "Đã hủy"};
        boolean isValidStatus = false;
        for (String status : validStatuses) {
            if (status.equalsIgnoreCase(selectedStatus)) {
                isValidStatus = true;
                break;
            }
        }
        if (!isValidStatus) {
            System.out.println("Order Status không hợp lệ: " + selectedStatus);
            request.setAttribute("error", "Trạng thái đơn hàng không hợp lệ.");
            request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
            return;
        }

        // Tiếp tục xử lý khi dữ liệu hợp lệ
        OrderManagementDAO ord = new OrderManagementDAO();
        try {
            // Cập nhật trạng thái đơn hàng
            ord.updateOrderStatus(order_id, selectedStatus);
            response.sendRedirect("OrderManagement"); // Chuyển hướng sau khi cập nhật thành công
        } catch (SQLException ex) {
            Logger.getLogger(UpdateOrderController.class.getName()).log(Level.SEVERE, null, ex);
            // Nếu có lỗi, sử dụng forward để hiển thị thông báo lỗi
            request.setAttribute("error", "Cập nhật trạng thái đơn hàng thất bại.");
            request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
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

}
