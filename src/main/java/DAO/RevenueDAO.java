/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Bang
 */
import DB_Context.DatabaseConnection;
import Model.Revenue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueDAO extends DatabaseConnection {

    public List<Revenue> getMonthlyRevenueByYear(int year) {
        List<Revenue> revenueList = new ArrayList<>();
        String sql = "SELECT month, year, total_revenue FROM Revenue WHERE year = ? ORDER BY month";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                int totalRevenue = rs.getInt("total_revenue");
                revenueList.add(new Revenue(month, year, totalRevenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueList;
    }
}