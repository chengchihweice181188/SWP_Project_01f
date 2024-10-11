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
import Model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DatabaseConnection {

    // CREATE - Thêm một category mới
public boolean createCategory(Category category) {
    String sql = "INSERT INTO Categories (category_name) VALUES (?)";
    
    try (Connection conn = getConnection()) {
        // Kiểm tra kết nối trước khi tiếp tục
        if (conn == null || conn.isClosed()) {
            System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
            return false;
        }

        // Tạo PreparedStatement và thực hiện câu lệnh INSERT
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            
            int affectedRows = ps.executeUpdate();

            // Kiểm tra số lượng dòng bị ảnh hưởng để xác nhận INSERT thành công
            if (affectedRows > 0) {
                System.out.println("Thêm danh mục thành công!");
                return true;
            } else {
                System.out.println("Thêm danh mục thất bại, không có dòng nào được chèn.");
            }
        }
    } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Tên danh mục đã tồn tại: " + e.getMessage());
        return false;
    } catch (SQLException e) {
        System.out.println("Lỗi khi thêm danh mục: " + e.getMessage());
        e.printStackTrace();
    }
    
    return false;
}

    // READ - Lấy tất cả categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setIsHidden(rs.getBoolean("is_hidden"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // READ - Lấy một category theo ID
    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    category.setIsHidden(rs.getBoolean("is_hidden"));
                    return category;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE - Cập nhật một category
    public boolean updateCategory(Category category) {
        String sql = "UPDATE Categories SET category_name = ?, is_hidden = ? WHERE category_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setBoolean(2, category.getIsHidden() != null ? category.getIsHidden() : false);
            ps.setInt(3, category.getCategoryId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE - Xóa một category theo ID
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
