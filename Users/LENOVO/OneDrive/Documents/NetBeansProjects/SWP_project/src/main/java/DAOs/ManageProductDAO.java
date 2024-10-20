/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ManageProductDAO {

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            try {
                String query = "SELECT p.product_id, p.product_name, p.product_description, p.product_image, "
                        + "p.product_price, c.category_name "
                        + "FROM Products p "
                        + "LEFT JOIN Categories c ON p.category_id = c.category_id "
                        + "WHERE p.is_hidden = 0";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product productVar = new Product();
                    productVar.setProduct_id(rs.getInt("product_id"));
                    productVar.setProduct_name(rs.getString("product_name"));
                    productVar.setProduct_image(rs.getString("product_image"));
                    productVar.setProduct_description(rs.getString("product_description"));
                    productVar.setProduct_price(rs.getDouble("product_price"));
                    productVar.setCategory_name(rs.getString("category_name"));
                    products.add(productVar); // Thêm sản phẩm vào danh sách
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    public int deleteProduct(String id) {
        Connection conn = DBConnection.getConnection();
        int count = 0;
        try {
            String sql = "UPDATE Products SET is_hidden = 1 WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            count = pst.executeUpdate();
        } catch (Exception e) {
            count = 0;
        }
        return count;
    }
}
