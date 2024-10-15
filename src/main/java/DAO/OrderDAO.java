/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB_Context.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Model.Order;

/**
 *
 * @author Bang
 */
public class OrderDAO extends DatabaseConnection {

    public List<Order> getOrdersWithFeedback() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, u.username, o.feedback_rating, o.feedback_comment, "
                + "o.order_date, o.order_status, o.payment_status, o.payment_method, o.order_price "
                + "FROM Orders o JOIN Users u ON o.user_id = u.user_id WHERE o.feedback_rating IS NOT NULL";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserName(rs.getString("username")); // Lấy từ bảng Users
                order.setFeedbackRating(rs.getInt("feedback_rating"));
                order.setFeedbackComment(rs.getString("feedback_comment"));
                order.setOrderDate(rs.getTimestamp("order_date")); // Nếu `order_date` là Date, có thể cần chuyển đổi
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentStatus(rs.getBoolean("payment_status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setOrderPrice(rs.getInt("order_price"));

                orders.add(order);
            }
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT o.*, u.username FROM Orders o JOIN Users u ON o.user_id = u.user_id WHERE o.order_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserName(rs.getString("username"));
                order.setOrderNote(rs.getString("order_note"));
                order.setOrderPrice(rs.getInt("order_price"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentStatus(rs.getBoolean("payment_status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setFeedbackRating(rs.getInt("feedback_rating"));
                order.setFeedbackComment(rs.getString("feedback_comment"));
                order.setUserId(rs.getInt("user_id"));
                order.setStaffId(rs.getInt("staff_id"));
                order.setVoucherId(rs.getInt("voucher_id"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

}
