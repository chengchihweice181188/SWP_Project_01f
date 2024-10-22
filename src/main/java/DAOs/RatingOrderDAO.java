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

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class RatingOrderDAO {

    public boolean addFeedback(int orderId, int rating, String comment) {
        String query = "UPDATE Orders SET feedback_rating = ?, feedback_comment = ? WHERE order_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setInt(1, rating);
            stmt.setString(2, comment);
            stmt.setInt(3, orderId);

            // Execute update
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Phương thức lấy feedback comment dựa trên orderId

    public String getFeedbackByOrderId(int orderId) {
        String query = "SELECT feedback_comment FROM Orders WHERE order_id = ?";
        String feedbackComment = null; // Chuỗi để chứa comment

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameter cho order_id
            stmt.setInt(1, orderId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    feedbackComment = rs.getString("feedback_comment"); // Lấy comment
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackComment; // Trả về feedback comment hoặc null nếu không có
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM Orders WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrder_id(rs.getInt("order_id"));
                order.setFeedback_rating(rs.getInt("feedback_rating"));
                order.setFeedback_comment(rs.getString("feedback_comment"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    // Method to update rating and review for a given order
    public boolean updateRating(int orderId, int rating, String review) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;

        try {
            // Open the connection to the database
            conn = DBConnection.getConnection();

            // Prepare the SQL update query
            String sql = "UPDATE Orders SET feedback_rating = ?, feedback_comment = ? WHERE order_id = ?";

            // Create the prepared statement
            preparedStatement = conn.prepareStatement(sql);

            // Set the parameters for the update query
            preparedStatement.setInt(1, rating); // feedback_rating
            preparedStatement.setString(2, review); // feedback_comment
            preparedStatement.setInt(3, orderId); // order_id

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if any rows were updated
            isUpdated = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (can replace with proper logging)
        }

        // Close the connection and statement if they were initialized
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception when closing the statement
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception when closing the connection
            }
        }

        return isUpdated;
    }

    public boolean deleteFeedbackByOrderId(int orderId) {
        String query = "UPDATE Orders SET feedback_rating = NULL, feedback_comment = NULL WHERE order_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameter cho order_id
            stmt.setInt(1, orderId);

            // Execute update query
            int rowsUpdated = stmt.executeUpdate();

            // Kiểm tra nếu có dòng nào được cập nhật (tức là xóa feedback thành công)
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
