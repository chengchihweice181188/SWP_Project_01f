/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB_Context;

/**
 *
 * @author Bang
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlserver://ASUS3DK:1433;databaseName=swp_pj_04f;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa123";

    public static Connection getConnection() {
        try {
            // Đăng ký driver JDBC cho SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại!");
            return null;
        }
    }
}
