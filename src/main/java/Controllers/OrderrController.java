/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderDAO;
import DAOs.CartItemDAO;
import DAOs.RatingOrderDAO;
import Models.CartItem;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class OrderrController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if ("Create".equals(action)) {
            createOrder(request, response);
        } else if ("Cancel".equals(action)) {
            cancelOrder(request, response);
        } else if ("UpdateFeedback".equals(action)) {
            updateFeedback(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("View".equals(action)) {
            viewOrders(request, response);
        } else if ("Detail".equals(action)) {
            viewOrderDetailAjax(request, response);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        String orderNote = request.getParameter("order_note");
        double orderPrice = Double.parseDouble(request.getParameter("order_price"));
        String orderStatus = "Đang chờ xử lý";
        java.util.Date orderDate = new java.util.Date();
        int paymentStatus = 0;
//        String paymentMethod = request.getParameter("payment_method");
        String paymentMethod = "Free";
        int feedbackRating = 0;
        String feedbackComment = "Chưa có comment";
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("Users");
        int userId = acc.getUser_id();
        int staffId = acc.getUser_id();
        String voucherCode = request.getParameter("discountCode");
        int voucherId = orderDAO.getVoucherById(voucherCode);
        if (voucherId == 0 && voucherCode == null) {
            response.sendRedirect("/Cart?error=Voucher không tồn tại.");
            return;
        }
        Order newOrder = new Order(0, orderNote, orderPrice, orderStatus, new java.sql.Date(orderDate.getTime()), paymentStatus, paymentMethod, feedbackRating, feedbackComment, userId, staffId, voucherId);
        newOrder = orderDAO.createOrder(newOrder);
        if (newOrder != null) {
            CartItemDAO cartDAO = new CartItemDAO();
            List<CartItem> cartItems = cartDAO.getCartItemsByUserId(userId);
            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail(0, newOrder.getOrder_id(), cartItem.getProduct_id(), cartItem.getProduct_option_id(), cartItem.getQuantity());
                orderDAO.addOrderDetail(orderDetail);
            }
            cartDAO.clearCart(userId);
            response.sendRedirect("/Order?action=View");
        } else {
            response.sendRedirect("/Order?action=View");
        }
    }

    private void updateFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String feedbackComment = request.getParameter("feedback_comment");
        int feedbackRating = Integer.parseInt(request.getParameter("feedback_rating"));

        OrderDAO orderDAO = new OrderDAO();
        boolean isUpdated = orderDAO.updateFeedback(orderId, feedbackComment, feedbackRating);

        if (isUpdated) {
            response.sendRedirect("/Order?action=View");
        } else {
            request.setAttribute("errorMessage", "Failed to update feedback.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/orderDetail.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void viewOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User acc = (User) session.getAttribute("Users");
            int uid = acc.getUser_id();
            OrderDAO orderDAO = new OrderDAO();
            RatingOrderDAO ratingOD = new RatingOrderDAO();
            try {
                // Lấy danh sách đơn hàng theo trạng thái
                List<Order> orders = orderDAO.viewOrders(uid, uid);

                // Kiểm tra từng đơn hàng để xem có feedback không
                for (Order order : orders) {
                    // Lấy feedback_comment cho từng order_id
                    String feedbackComment = ratingOD.getFeedbackByOrderId(order.getOrder_id());
                    // Đánh dấu nếu đơn hàng đã có feedback
                    order.setFeedbackExists(feedbackComment != null && !feedbackComment.isEmpty());
                }
                request.setAttribute("orders", orders);

            } catch (Exception ex) {
                Logger.getLogger(OrderrController.class.getName()).log(Level.SEVERE, "System Error: ", ex);
                request.setAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("order.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/login.jsp");
        }
    }

    private void viewOrderDetailAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("order_id"));
            OrderDAO orderDAO = new OrderDAO();
            CartItemDAO cartItemDAO = new CartItemDAO();

            List<OrderDetail> orderDetail = orderDAO.viewOrderDetail(orderId);
            List<Product> products = new ArrayList<>();

            for (OrderDetail item : orderDetail) {
                Product product = cartItemDAO.getProductById(item.getProduct_id());
                products.add(product);
            }

            request.setAttribute("orderDetail", orderDetail);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/orderDetail.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/login.jsp");
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        OrderDAO orderDAO = new OrderDAO();
        boolean isCancelled = orderDAO.cancelOrder(orderId);

        if (isCancelled) {
            response.sendRedirect("/Order?action=View");
        } else {
            response.sendRedirect("/Order?action=View");
        }
    }
}
