/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class LoginDAO {

    public static User Validate(String username, String password) throws SQLException { // Thêm throws SQLException
        User account = null; // Khởi tạo account là null trước
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Chỉ khởi tạo đối tượng User khi có kết quả hợp lệ
                account = new User();
                account.setUser_id(rs.getInt("user_id"));
                account.setUsername(rs.getString("username"));
                account.setEmail(rs.getString("email"));
                account.setPhone_number(rs.getString("phone_number"));
                account.setAddress(rs.getString("address"));
                account.setPoint(rs.getInt("point"));
                account.setRole(rs.getInt("role"));
            }
        } finally {
            // Đảm bảo tài nguyên luôn được đóng dù có xảy ra lỗi hay không
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return account;
    }
}
