/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class ProductDao {

    // Lấy báo cáo tất cả sản phẩm
    public List<Object[]> getProductSalesReport() {
        List<Object[]> productReports = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.product_price, COALESCE(SUM(od.quantity), 0) AS total_quantity "
                + "FROM Products p "
                + "LEFT JOIN OrderDetails od ON p.product_id = od.product_id "
                + "GROUP BY p.product_id, p.product_name, p.product_price";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] productReport = new Object[4];
                productReport[0] = rs.getInt("product_id");  // Product ID
                productReport[1] = rs.getString("product_name");  // Product Name
                productReport[2] = rs.getDouble("product_price");  // Product Price
                productReport[3] = rs.getInt("total_quantity");  // Total Quantity
                productReports.add(productReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productReports;
    }

    // Lấy báo cáo sản phẩm bán trong tuần
    public List<Object[]> getWeeklyProductSalesReport() {
        List<Object[]> productReports = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.product_price, "
                + "COALESCE(SUM(od.quantity), 0) AS total_quantity "
                + "FROM Products p "
                + "LEFT JOIN OrderDetails od ON p.product_id = od.product_id "
                + "LEFT JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE o.order_date >= DATEADD(DAY, -7, CAST(GETDATE() AS DATE)) "
                + "GROUP BY p.product_id, p.product_name, p.product_price";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] productReport = new Object[4];
                productReport[0] = rs.getInt("product_id");  // Product ID
                productReport[1] = rs.getString("product_name");  // Product Name
                productReport[2] = rs.getDouble("product_price");  // Product Price
                productReport[3] = rs.getInt("total_quantity");  // Total Quantity
                productReports.add(productReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productReports;
    }

    // Lấy báo cáo sản phẩm bán trong tháng
    public List<Object[]> getMonthlyProductSalesReport() {
        List<Object[]> productReports = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.product_price, "
                + "COALESCE(SUM(od.quantity), 0) AS total_quantity "
                + "FROM Products p "
                + "LEFT JOIN OrderDetails od ON p.product_id = od.product_id "
                + "LEFT JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE o.order_date >= DATEADD(MONTH, -1, CAST(GETDATE() AS DATE)) "
                + "GROUP BY p.product_id, p.product_name, p.product_price";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] productReport = new Object[4];
                productReport[0] = rs.getInt("product_id");  // Product ID
                productReport[1] = rs.getString("product_name");  // Product Name
                productReport[2] = rs.getDouble("product_price");  // Product Price
                productReport[3] = rs.getInt("total_quantity");  // Total Quantity
                productReports.add(productReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productReports;
    }
}
