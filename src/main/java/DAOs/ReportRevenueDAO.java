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
 * @author tvhun
 */
public class ReportRevenueDAO {

    private final Connection conn;

    public ReportRevenueDAO() {
        conn = DBConnection.getConnection();
    }

    public List<Order> getRevenueByDay() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, order_note, order_price, order_date FROM Orders WHERE CAST(order_date AS DATE) = CAST(GETDATE() AS DATE)";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_note"),
                    rs.getDouble("order_price"),
                    null, rs.getDate("order_date"), 0, null, 0, null, 0, 0, 0, null
            );
            orders.add(order);
        }
        return orders;
    }

    public List<Order> getRevenueByWeek() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, order_note, order_price, order_date FROM Orders WHERE DATEDIFF(WEEK, order_date, GETDATE()) = 0";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_note"),
                    rs.getDouble("order_price"),
                    null, rs.getDate("order_date"), 0, null, 0, null, 0, 0, 0, null
            );
            orders.add(order);
        }
        return orders;
    }

    public List<Order> getRevenueByMonth() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, order_note, order_price, order_date FROM Orders WHERE DATEDIFF(MONTH, order_date, GETDATE()) = 0";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_note"),
                    rs.getDouble("order_price"),
                    null, rs.getDate("order_date"), 0, null, 0, null, 0, 0, 0, null
            );
            orders.add(order);
        }
        return orders;
    }

    public List<Order> getRevenueByDateRange(String startDate, String endDate) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, order_note, order_price, order_date FROM Orders WHERE order_date BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, startDate);
        ps.setString(2, endDate);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_note"),
                    rs.getDouble("order_price"),
                    null, rs.getDate("order_date"), 0, null, 0, null, 0, 0, 0, null
            );
            orders.add(order);
        }
        return orders;
    }
}
