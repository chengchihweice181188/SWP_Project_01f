/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RatingOrderDAO;
import Models.Order;
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
public class ViewRatingController extends HttpServlet {

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
            out.println("<title>Servlet ViewRatingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewRatingController at " + request.getContextPath() + "</h1>");
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

        // Initialize DAO to fetch the order's feedback
        RatingOrderDAO orderDAO = new RatingOrderDAO();
        Order order = orderDAO.getOrderById(Integer.parseInt(orderId));

        // Truyền feedback_rating và feedback_comment tới trang JSP để hiển thị
        request.setAttribute("order_id", orderId);
        request.setAttribute("rating", order.getFeedback_rating());
        request.setAttribute("review", order.getFeedback_comment());

        // Chuyển hướng đến trang viewRatingOrder.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewRatingOrder.jsp");
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
        // Retrieve the parameters from the form
        String orderIdParam = request.getParameter("order_id");
        String ratingParam = request.getParameter("rating");
        String reviewParam = request.getParameter("review");

        // Convert order ID and rating to integer (since they're stored as numbers in the DB)
        int orderId = 0;
        int rating = 0;
        try {
            orderId = Integer.parseInt(orderIdParam);
            rating = Integer.parseInt(ratingParam);
        } catch (NumberFormatException e) {
            // Handle error if parsing fails
            request.setAttribute("errorMessage", "Invalid order ID or rating.");
            request.getRequestDispatcher("viewRatingOrder.jsp").forward(request, response);
            return;
        }

        // Update the rating and review in the database
        RatingOrderDAO ratingOrderDAO = new RatingOrderDAO();
        boolean updateSuccess = ratingOrderDAO.updateRating(orderId, rating, reviewParam);

        // Check if update was successful
        if (updateSuccess) {
            // If successful, redirect to a success page or the updated rating page
            request.setAttribute("successMessage", "Cập nhật đánh giá thành công.");
            request.getRequestDispatcher("viewRatingOrder.jsp").forward(request, response);
        } else {
            // If the update failed, return an error message
            request.setAttribute("errorMessage", "Cập nhật đánh giá thất bại !");
            request.getRequestDispatcher("viewRatingOrder.jsp").forward(request, response);
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
