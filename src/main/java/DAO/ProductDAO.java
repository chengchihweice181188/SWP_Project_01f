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
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProductDAO extends DatabaseConnection {

    // Trả về danh sách best seller theo từng tháng cho năm đã chọn, nhưng không tính tháng hiện tại
    public Map<Integer, Product> getBestSellersByYear(int year, int currentMonth) throws SQLException, ClassNotFoundException {
        Map<Integer, Product> bestSellers = new HashMap<>();
        try ( Connection conn = getConnection()) {
            // Kiểm tra xem có phải là năm hiện tại hay không
            int currentYear = LocalDate.now().getYear();

            // Câu lệnh SQL nếu là năm hiện tại thì cần thêm điều kiện tháng, nếu không thì không cần
            String sql;
            if (year == currentYear) {
                // Nếu là năm hiện tại, chỉ lấy đến tháng hiện tại
                sql = "WITH RankedProducts AS ("
                        + "SELECT MONTH(O.order_date) AS order_month, "
                        + "P.product_id, P.product_name, P.product_price, "
                        + "SUM(OD.quantity) AS total_quantity, "
                        + "ROW_NUMBER() OVER (PARTITION BY MONTH(O.order_date) ORDER BY SUM(OD.quantity) DESC) AS rank "
                        + "FROM Orders O "
                        + "JOIN OrderDetails OD ON O.order_id = OD.order_id "
                        + "JOIN Products P ON OD.product_id = P.product_id "
                        + "WHERE YEAR(O.order_date) = ? AND MONTH(O.order_date) <= ? " // Điều kiện tháng cho năm hiện tại
                        + "AND P.is_hidden = 0 "
                        + "GROUP BY P.product_id, P.product_name, P.product_price, MONTH(O.order_date) "
                        + ") "
                        + "SELECT order_month, product_id, product_name, product_price, total_quantity "
                        + "FROM RankedProducts "
                        + "WHERE rank = 1 "
                        + "ORDER BY order_month;";
            } else {
                // Nếu là các năm trước, lấy toàn bộ 12 tháng
                sql = "WITH RankedProducts AS ("
                        + "SELECT MONTH(O.order_date) AS order_month, "
                        + "P.product_id, P.product_name, P.product_price, "
                        + "SUM(OD.quantity) AS total_quantity, "
                        + "ROW_NUMBER() OVER (PARTITION BY MONTH(O.order_date) ORDER BY SUM(OD.quantity) DESC) AS rank "
                        + "FROM Orders O "
                        + "JOIN OrderDetails OD ON O.order_id = OD.order_id "
                        + "JOIN Products P ON OD.product_id = P.product_id "
                        + "WHERE YEAR(O.order_date) = ? " // Không giới hạn tháng cho các năm trước
                        + "AND P.is_hidden = 0 "
                        + "GROUP BY P.product_id, P.product_name, P.product_price, MONTH(O.order_date) "
                        + ") "
                        + "SELECT order_month, product_id, product_name, product_price, total_quantity "
                        + "FROM RankedProducts "
                        + "WHERE rank = 1 "
                        + "ORDER BY order_month;";
            }

            // Chuẩn bị PreparedStatement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, year);  // Gán giá trị cho tham số year

            // Nếu là năm hiện tại thì gán thêm tham số tháng
            if (year == currentYear) {
                stmt.setInt(2, currentMonth);  // Gán giá trị cho tham số currentMonth nếu là năm hiện tại
            }

            ResultSet rs = stmt.executeQuery();

            // Đọc kết quả truy vấn
            while (rs.next()) {
                int month = rs.getInt("order_month");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int productPrice = rs.getInt("product_price");
                int totalQuantity = rs.getInt("total_quantity");

                Product product = new Product(productId, productName, productPrice, totalQuantity);
                bestSellers.put(month, product);
            }
        }
        return bestSellers;
    }
}
