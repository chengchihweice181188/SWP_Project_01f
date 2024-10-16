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

public class RegisterDAO {

    public boolean isUserExists(String username) {
        try ( Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();  // Nếu có bản ghi thì trả về true
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
            String insertQuery = "INSERT INTO Users (username, password, email,role) VALUES (?, ?, ?, 2)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, email);
            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows > 0) {
                return "SUCCESS";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while processing your request.";
        }
        return "An error occurred while processing your request.";
    }
}
