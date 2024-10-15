/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class OrderManagementDAO {

    public List<Order> getOrdersByStatus(String orderStatus) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT o.*, u.username "
                + "FROM Orders o "
                + "JOIN Users u ON o.user_id = u.user_id ";
        // Thêm điều kiện nếu orderStatus không phải là "all"
        if (!orderStatus.equals("all")) {
            query += "WHERE o.order_status = ? ";
        }
        query += "ORDER BY o.order_date DESC";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            // Nếu có điều kiện lọc theo trạng thái, gán giá trị cho tham số
            if (!orderStatus.equals("all")) {
                ps.setString(1, orderStatus);
            }
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrder_id(rs.getInt("order_id"));
                    order.setOrder_note(rs.getString("order_note"));
                    order.setOrder_price(rs.getDouble("order_price"));
                    order.setOrder_status(rs.getString("order_status"));
                    order.setOrder_date(rs.getDate("order_date"));
                    order.setPayment_status(rs.getInt("payment_status"));
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setFeedback_rating(rs.getInt("feedback_rating"));
                    order.setFeedback_comment(rs.getString("feedback_comment"));
                    order.setUser_id(rs.getInt("user_id"));
                    order.setStaff_id(rs.getInt("staff_id"));
                    order.setVoucher_id(rs.getInt("voucher_id"));
                    order.setUsername(rs.getString("username"));
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    // Phương thức cập nhật trạng thái đơn hàng
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE Orders SET order_status = ? WHERE order_id = ?";
        try ( Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(true);  // Đảm bảo auto-commit được bật
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
    }
}
