/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Option;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ViewCategoryDAO {

    public List getProductsByCategory(String categoryId) {
        List<Product> products = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                String query = "SELECT * FROM Products WHERE is_hidden = 0 AND category_id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, categoryId);
                rs = ps.executeQuery();
                //Lưu sản phẩm vào list
                while (rs.next()) {
                    Product product = new Product();
                    product.setProduct_id(rs.getInt("product_id"));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setProduct_price(rs.getDouble("product_price"));
                    product.setProduct_image(rs.getString("product_image"));
                    product.setProduct_description(rs.getString("product_description"));
                    products.add(product);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    public ResultSet getAllCategories() {
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT * FROM Categories WHERE is_hidden = 0");
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet getAllProducts() {
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT * FROM Products WHERE is_hidden = 0");
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet getOptionsByProductId(int productId) throws SQLException {
        String query = "SELECT * FROM Options o "
                + "JOIN ProductOptions po ON o.option_id = po.option_id "
                + "WHERE po.product_id = ?";
        Connection conn = DBConnection.getConnection(); // Đảm bảo rằng DBConnection có phương thức getConnection
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        return ps.executeQuery();
    }

}
