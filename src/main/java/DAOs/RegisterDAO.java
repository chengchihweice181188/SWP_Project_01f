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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterDAO {

    // Phương thức mã hóa MD5
    private String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUserExists(String username) {
        try ( Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        try ( Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String registerUser(String username, String password, String email) {
        try ( Connection connection = DBConnection.getConnection()) {
            // Mã hóa mật khẩu bằng MD5
            String hashedPassword = hashPasswordMD5(password);
            if (hashedPassword == null) {
                return "Password hashing failed.";
            }
            String insertQuery = "INSERT INTO Users (username, password, email, role) VALUES (?,?,?, 2)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, hashedPassword);
            insertStatement.setString(3, email);

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                return "SUCCESS";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "An error occurred while processing your request.";
        }
        return "An error occurred while processing your request.";
    }
}
