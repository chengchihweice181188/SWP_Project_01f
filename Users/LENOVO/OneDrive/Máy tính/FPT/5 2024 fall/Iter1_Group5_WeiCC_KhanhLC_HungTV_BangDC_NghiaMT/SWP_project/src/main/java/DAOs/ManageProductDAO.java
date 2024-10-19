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
import java.sql.Statement;
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

    public Product getProductById(String productId) {
        Product productVar = null;
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            try {
                String query = "SELECT p.product_name, p.product_description, p.product_image, "
                        + "p.product_price, p.category_id, p.product_id "
                        + "FROM Products p "
                        + "WHERE p.product_id = ? AND p.is_hidden = 0";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                // Kiểm tra xem kết quả có hay không
                if (rs.next()) {
                    productVar = new Product();
                    productVar.setProduct_name(rs.getString("product_name"));
                    productVar.setProduct_image(rs.getString("product_image"));
                    productVar.setProduct_description(rs.getString("product_description"));
                    productVar.setProduct_price(rs.getDouble("product_price"));
                    productVar.setCategory_id(rs.getInt("category_id"));
                    productVar.setProduct_id(rs.getInt("product_id"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productVar;
    }

    public int deleteProduct(String productId) {
        Connection conn = DBConnection.getConnection();
        int count = 0;
        try {
            // Ẩn sản phẩm (cập nhật is_hidden = 1)
            String sql = "UPDATE Products SET is_hidden = 1 WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, productId);
            count = pst.executeUpdate();
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    public int deleteProductOptions(String productId) {
        Connection conn = DBConnection.getConnection();
        int count = 0;
        try {
            // Cập nhật tất cả các ProductOption liên quan
            String updateProductOptionsSql = "UPDATE ProductOptions SET is_hidden = 1 WHERE product_id = ?";
            PreparedStatement updateProductOptionsStmt = conn.prepareStatement(updateProductOptionsSql);
            updateProductOptionsStmt.setString(1, productId);
            count = updateProductOptionsStmt.executeUpdate();
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    public int addProduct(Product product) {
        Connection conn = null;
        PreparedStatement psProduct = null;
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Products (product_name, product_description, product_price, product_image, category_id) "
                    + "VALUES (?, ?, ?, ?, ?)";
            psProduct = conn.prepareStatement(sql);
            psProduct.setString(1, product.getProduct_name());
            psProduct.setString(2, product.getProduct_description());
            psProduct.setDouble(3, product.getProduct_price());
            psProduct.setString(4, product.getProduct_image());
            psProduct.setInt(5, product.getCategory_id());
            count = psProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement psProduct = null;
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE Products SET product_name = ?, product_description = ?, product_price = ?, "
                    + "product_image = ?, category_id = ? WHERE product_id = ?";
            psProduct = conn.prepareStatement(sql);
            psProduct.setString(1, product.getProduct_name());
            psProduct.setString(2, product.getProduct_description());
            psProduct.setDouble(3, product.getProduct_price());
            psProduct.setString(4, product.getProduct_image());
            psProduct.setInt(5, product.getCategory_id());
            psProduct.setInt(6, product.getProduct_id());
            count = psProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int updateProductWithoutImg(Product product) {
        Connection conn = null;
        PreparedStatement psProduct = null;
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE Products SET product_name = ?, product_description = ?, product_price = ?, "
                    + " category_id = ? WHERE product_id = ?";
            psProduct = conn.prepareStatement(sql);
            psProduct.setString(1, product.getProduct_name());
            psProduct.setString(2, product.getProduct_description());
            psProduct.setDouble(3, product.getProduct_price());
            psProduct.setInt(4, product.getCategory_id());
            psProduct.setInt(5, product.getProduct_id());
            count = psProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
