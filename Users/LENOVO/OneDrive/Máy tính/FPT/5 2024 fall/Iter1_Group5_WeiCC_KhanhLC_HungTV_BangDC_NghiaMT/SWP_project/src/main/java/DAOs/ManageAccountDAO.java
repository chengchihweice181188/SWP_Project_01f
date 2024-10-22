/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Product;
import Models.User;
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
public class ManageAccountDAO {

    public List<User> getAllAccount() {
        List<User> users = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            try {
                String query = "SELECT user_id, email, role, user_status FROM Users WHERE role != 0";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    User userVar = new User();
                    userVar.setUser_id(rs.getInt("user_id"));
                    userVar.setEmail(rs.getString("email"));
                    userVar.setRole(rs.getInt("role"));
                    userVar.setUser_status(rs.getInt("user_status"));
                    users.add(userVar); // Thêm sản phẩm vào danh sách
                }
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }

    public int updateUserStatus(User user) {
        Connection conn = DBConnection.getConnection();
        int rowsUpdated = 0;
        if (conn != null) {
            try {
                String query = "UPDATE Users SET user_status = ? WHERE user_id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, user.getUser_status());
                ps.setInt(2, user.getUser_id());
                rowsUpdated = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ViewCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowsUpdated;
    }
}
