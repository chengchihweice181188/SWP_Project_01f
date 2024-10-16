/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class UpdateProfileDAO {
    // Phương thức cập nhật thông tin tài khoản người dùng
    public boolean updateAccount(User user) throws SQLException {
        String sql = "UPDATE Users SET email = ?, phone_number = ?, address = ? WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // Lấy kết nối đến cơ sở dữ liệu
            conn = DBConnection.DBConnection.getConnection();
            // Tạo câu lệnh chuẩn bị (PreparedStatement) với câu truy vấn SQL
            ps = conn.prepareStatement(sql);
            // Thiết lập các giá trị cho câu truy vấn
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPhone_number());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getUsername()); // Tìm theo username
            // Thực thi câu lệnh SQL và kiểm tra kết quả
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng kết nối và tài nguyên sau khi sử dụng
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }
}
