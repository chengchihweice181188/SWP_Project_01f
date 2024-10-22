/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RatingOrderDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class CreateRatingController extends HttpServlet {

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
            out.println("<title>Servlet CreateRatingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateRatingController at " + request.getContextPath() + "</h1>");
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
        // Lấy order_id từ yêu cầu GET
        String orderId = request.getParameter("order_id");

        // Kiểm tra xem order_id có hợp lệ hay không
        if (orderId == null || orderId.isEmpty()) {
            request.setAttribute("errorMessage", "Đơn hàng không hợp lệ.");
            request.getRequestDispatcher("orderList.jsp").forward(request, response);
            return;
        }
        // Truyền order_id tới trang JSP để hiển thị trong form
        request.setAttribute("order_id", orderId);
        // Chuyển hướng đến trang ratingOrder.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("ratingOrder.jsp");
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
        // Get parameters from the form
        String orderId = request.getParameter("order_id");
        String content_rating = request.getParameter("review");
        String ratingStar = request.getParameter("rating");

        // Initialize DAO
        RatingOrderDAO ratingOD = new RatingOrderDAO();
        boolean isSuccess = false;
        int order_id = 0;
        int rating_star = 0;
        // Convert order_id from String to int
        try {
            order_id = Integer.parseInt(orderId);
            rating_star = Integer.parseInt(ratingStar);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Đơn hàng không hợp lệ.");
            request.getRequestDispatcher("ratingOrder.jsp").forward(request, response);
            return;
        }

        // Add feedback using DAO
        try {
            isSuccess = ratingOD.addFeedback(order_id, rating_star, content_rating);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
            request.getRequestDispatcher("ratingOrder.jsp").forward(request, response);
            return;
        }

        // If feedback submission is successful, redirect to a success page or another page
        if (isSuccess) {
            request.setAttribute("successMessage", "Cảm ơn bạn đã đánh giá sản phẩm.");
            request.getRequestDispatcher("ratingOrder.jsp").forward(request, response);
        } else {
            // If feedback submission fails, show an error message
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại.");
            request.getRequestDispatcher("ratingOrder.jsp").forward(request, response);
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
