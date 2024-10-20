package DAOs;

import Models.Order;
import DBConnection.DBConnection;
import Models.OrderDetail;
import Models.Voucher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public Order createOrder(Order order) {
        String insertSql = "INSERT INTO orders (order_note, order_price, order_status, order_date, payment_status, payment_method, feedback_rating, feedback_comment, user_id, staff_id, voucher_id) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String selectLastSql = "SELECT TOP 1 * FROM orders ORDER BY order_id DESC"; 
        if (order.getVoucher_id() == 0){
            insertSql = "INSERT INTO orders (order_note, order_price, order_status, order_date, payment_status, payment_method, feedback_rating, feedback_comment, user_id, staff_id, voucher_id) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertPs = conn.prepareStatement(insertSql)) {

            insertPs.setString(1, order.getOrder_note());
            insertPs.setDouble(2, order.getOrder_price());
            insertPs.setString(3, order.getOrder_status());
            insertPs.setDate(4, new java.sql.Date(order.getOrder_date().getTime()));
            insertPs.setInt(5, order.getPayment_status());
            insertPs.setString(6, order.getPayment_method());
            insertPs.setInt(7, order.getFeedback_rating());
            insertPs.setString(8, order.getFeedback_comment());
            insertPs.setInt(9, order.getUser_id());
            insertPs.setInt(10, order.getStaff_id());
            if (order.getVoucher_id() != 0){
                insertPs.setInt(11, order.getVoucher_id());
            }
            int affectedRows = insertPs.executeUpdate();
            if (affectedRows > 0) {
                try (PreparedStatement selectPs = conn.prepareStatement(selectLastSql);
                     ResultSet resultSet = selectPs.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToOrder(resultSet);
                    } else {
                        throw new SQLException("Order retrieval failed, no record found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public int getVoucherById(String voucherCode) {
        int voucherId = 0;
        String query = "SELECT * FROM vouchers WHERE voucher_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, voucherCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                voucherId = rs.getInt("voucher_id");
                double discountAmount = rs.getDouble("voucher_discount");
                            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherId; 
    }
    
    public boolean updateFeedback(int orderId, String feedbackComment, int feedbackRating) {
        String query = "UPDATE Orders SET feedback_comment = ?, feedback_rating = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, feedbackComment);
            stmt.setInt(2, feedbackRating);
            stmt.setInt(3, orderId);

            return stmt.executeUpdate() > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> viewOrders(int userId, int staffId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? OR staff_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, staffId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<OrderDetail> viewOrderDetail(int orderId) {
        List<OrderDetail> orderDetail = new ArrayList<>();
        String sql = "SELECT * FROM orderDetails WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderDetail.add(new OrderDetail(
                    rs.getInt("order_detail_id"),
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("product_option_id"),
                    rs.getInt("quantity")
                 ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE orders SET order_status = N'Đã hủy' WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        int order_id = rs.getInt("order_id");
        String order_note = rs.getString("order_note");
        double order_price = rs.getDouble("order_price");
        String order_status = rs.getString("order_status");
        Date order_date = rs.getDate("order_date");
        int payment_status = rs.getInt("payment_status");
        String payment_method = rs.getString("payment_method");
        int feedback_rating = rs.getInt("feedback_rating");
        String feedback_comment = rs.getString("feedback_comment");
        int user_id = rs.getInt("user_id");
        int staff_id = rs.getInt("staff_id");
        int voucher_id = rs.getInt("voucher_id");

        return new Order(order_id, order_note, order_price, order_status, order_date, payment_status, payment_method, feedback_rating, feedback_comment, user_id, staff_id, voucher_id);
    }
    
    public void addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO orderdetails (order_id, product_id, product_option_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrder_id());
            stmt.setInt(2, orderDetail.getProduct_id());
            stmt.setInt(3, orderDetail.getProduct_option_id());
            stmt.setInt(4, orderDetail.getQuantity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
