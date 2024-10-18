/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class RatingDAO {

    private final Connection conn;

    public RatingDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Order> getAllRatings() throws SQLException {
        List<Order> ratingList = new ArrayList<>();
        String query = "SELECT order_id, user_id, feedback_rating, feedback_comment FROM [Orders] WHERE feedback_rating IS NOT NULL";
        try ( PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int user_id = rs.getInt("user_id");
                int feedback_rating = rs.getInt("feedback_rating");
                String feedback_comment = rs.getString("feedback_comment");
                Order rating = new Order(order_id, null, 0.0, null, null, 0, null, feedback_rating, feedback_comment, user_id, 0, 0, null);
                ratingList.add(rating);
            }
        }
        return ratingList;
    }

    public List<Order> getRatingsByDay() throws SQLException {
        return getRatingsByPeriod("DAY");
    }

    public List<Order> getRatingsByWeek() throws SQLException {
        return getRatingsByPeriod("WEEK");
    }

    public List<Order> getRatingsByMonth() throws SQLException {
        return getRatingsByPeriod("MONTH");
    }

    private List<Order> getRatingsByPeriod(String period) throws SQLException {
        List<Order> ratingList = new ArrayList<>();
        String query = "SELECT order_id, user_id, feedback_rating, feedback_comment "
                + "FROM [Orders] WHERE feedback_rating IS NOT NULL "
                + "AND order_date >= DATEADD(" + period + ", -1, GETDATE())";
        try ( PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int user_id = rs.getInt("user_id");
                int feedback_rating = rs.getInt("feedback_rating");
                String feedback_comment = rs.getString("feedback_comment");
                Order rating = new Order(order_id, null, 0.0, null, null, 0, null, feedback_rating, feedback_comment, user_id, 0, 0, null);
                ratingList.add(rating);
            }
        }
        return ratingList;
    }

}
