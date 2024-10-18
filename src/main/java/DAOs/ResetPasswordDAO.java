/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class ResetPasswordDAO {

    // Phương thức kiểm tra email có tồn tại trong cơ sở dữ liệu hay không
    public boolean checkEmailExists(String email) throws SQLException {
        String query = "SELECT user_id FROM Users WHERE email = ?";

        // Kết nối đến cơ sở dữ liệu
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            // Thiết lập tham số cho câu truy vấn
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            // Nếu có kết quả trả về, nghĩa là email đã tồn tại
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Phương thức cập nhật mật khẩu trực tiếp dựa trên email
    public boolean updatePasswordByEmail(String email, String newPassword) {
        String query = "UPDATE Users SET password = ? WHERE email = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            // Thiết lập các tham số cho PreparedStatement (không mã hóa mật khẩu)
            stmt.setString(1, newPassword);
            stmt.setString(2, email);

            // Thực thi câu lệnh SQL
            int rowsUpdated = stmt.executeUpdate();

            // Kiểm tra xem có hàng nào bị ảnh hưởng không (tức là mật khẩu đã được cập nhật thành công)
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
