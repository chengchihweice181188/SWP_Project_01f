/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Category;
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

    public List getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            try {
                String query = "SELECT * FROM Categories WHERE is_hidden = 0";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                //Lưu sản phẩm vào list
                while (rs.next()) {
                    Category categoryVar = new Category();
                    categoryVar.setCategory_id(rs.getInt("category_id"));
                    categoryVar.setCategory_name(rs.getString("category_name"));
                    categories.add(categoryVar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categories;
    }

    public List<Product> getAllProductInfo() {
        List<Product> products = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            try {
                String query = "SELECT p.product_id, p.product_name, p.product_description, p.product_image, "
                        + "p.product_price, o.option_id, o.option_name, o.price_adjustment "
                        + "FROM Products p "
                        + "LEFT JOIN ProductOptions po ON p.product_id = po.product_id "
                        + "LEFT JOIN Options o ON po.option_id = o.option_id "
                        + "WHERE p.is_hidden = 0";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                int currentProductId = -1;
                Product productVar = null;
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    // Nếu đây là sản phẩm mới, tạo một đối tượng Product mới
                    if (productId != currentProductId) {
                        if (productVar != null) {
                            products.add(productVar); // Thêm sản phẩm trước đó vào danh sách
                        }
                        productVar = new Product();
                        productVar.setProduct_id(productId);
                        productVar.setProduct_name(rs.getString("product_name"));
                        productVar.setProduct_description(rs.getString("product_description"));
                        productVar.setProduct_image(rs.getString("product_image"));
                        productVar.setProduct_price(rs.getDouble("product_price"));
                        productVar.setOptions(new ArrayList<Option>()); // Khởi tạo danh sách option cho sản phẩm
                        currentProductId = productId;
                    }
                    // Nếu có option (option_id không null), thêm vào danh sách option của sản phẩm
                    int optionId = rs.getInt("option_id");
                    if (optionId != 0) {
                        Option option = new Option();
                        option.setOption_id(optionId);
                        option.setOption_name(rs.getString("option_name"));
                        option.setPrice_adjustment(rs.getInt("price_adjustment"));
                        productVar.getOptions().add(option); // Thêm option vào danh sách của sản phẩm
                    }
                }
                // Thêm sản phẩm cuối cùng vào danh sách (nếu có)
                if (productVar != null) {
                    products.add(productVar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    public List<Product> getAllProductInfoByCategory(String categoryId) {
    List<Product> products = new ArrayList<>();
    Connection conn = DBConnection.getConnection();
    if (conn != null) {
        try {
            // Sửa câu truy vấn để lọc theo category_id
            String query = "SELECT p.product_id, p.product_name, p.product_description, p.product_image, "
                         + "p.product_price, o.option_id, o.option_name, o.price_adjustment "
                         + "FROM Products p "
                         + "LEFT JOIN ProductOptions po ON p.product_id = po.product_id "
                         + "LEFT JOIN Options o ON po.option_id = o.option_id "
                         + "WHERE p.is_hidden = 0 AND p.category_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();
            //Dòng này để giúp so sánh bên dưới luôn lấy sản phẩm mới khi gom các option lại thành 1 nhóm.
            int currentProductId = -1;
            Product productVar = null;
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                // Nếu đây là sản phẩm mới, tạo một đối tượng Product mới
                if (productId != currentProductId) {
                    if (productVar != null) {
                        products.add(productVar); // Thêm sản phẩm trước đó vào danh sách
                    }
                    productVar = new Product();
                    productVar.setProduct_id(productId);
                    productVar.setProduct_name(rs.getString("product_name"));
                    productVar.setProduct_description(rs.getString("product_description"));
                    productVar.setProduct_image(rs.getString("product_image"));
                    productVar.setProduct_price(rs.getDouble("product_price"));
                    productVar.setOptions(new ArrayList<Option>()); // Khởi tạo danh sách option cho sản phẩm
                    currentProductId = productId;
                }
                // Nếu có option (option_id không null), thêm vào danh sách option của sản phẩm
                int optionId = rs.getInt("option_id");
                if (optionId != 0) {
                    Option option = new Option();
                    option.setOption_id(optionId);
                    option.setOption_name(rs.getString("option_name"));
                    option.setPrice_adjustment(rs.getInt("price_adjustment"));
                    productVar.getOptions().add(option); // Thêm option vào danh sách của sản phẩm
                }
            }
            // Thêm sản phẩm cuối cùng vào danh sách (nếu có)
            if (productVar != null) {
                products.add(productVar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return products;
}

}
