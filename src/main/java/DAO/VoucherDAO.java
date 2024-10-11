/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB_Context.DatabaseConnection;
import Model.Voucher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Bang
 */
public class VoucherDAO extends DatabaseConnection {
     // READ - Lấy tất cả voucher
    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE is_hidden = 0";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherID(rs.getInt("voucher_id"));
                voucher.setVoucherCode(rs.getString("voucher_code"));
                voucher.setVoucherDiscount(rs.getInt("voucher_discount"));
                voucher.setVoucherValidFrom(rs.getTimestamp("voucher_valid_from").toLocalDateTime());
                voucher.setVoucherValidTo(rs.getTimestamp("voucher_valid_to").toLocalDateTime());
                voucher.setVoucherStatus(rs.getBoolean("voucher_status"));
                voucher.setIsHidden(rs.getBoolean("is_hidden"));
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    // CREATE - Thêm một voucher mới
    public boolean createVoucher(Voucher voucher) {
        String sql = "INSERT INTO Vouchers (voucher_code, voucher_discount, voucher_valid_from, voucher_valid_to, voucher_status, is_hidden) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, voucher.getVoucherCode());
                ps.setInt(2, voucher.getVoucherDiscount());
                ps.setTimestamp(3, Timestamp.valueOf(voucher.getVoucherValidFrom()));
                ps.setTimestamp(4, Timestamp.valueOf(voucher.getVoucherValidTo()));
                ps.setBoolean(5, voucher.getVoucherStatus());
                ps.setBoolean(6, voucher.getIsHidden());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Thêm voucher thành công!");
                    return true;
                } else {
                    System.out.println("Thêm voucher thất bại, không có dòng nào được chèn.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vi phạm ràng buộc dữ liệu (ví dụ: voucher_code đã tồn tại): " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm voucher: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // UPDATE - Cập nhật thông tin voucher
    public boolean updateVoucher(Voucher voucher) {
        String sql = "UPDATE Vouchers SET voucher_code = ?, voucher_discount = ?, voucher_valid_from = ?, voucher_valid_to = ?, voucher_status = ?, is_hidden = ? WHERE voucher_id = ?";

        try (Connection conn = getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, voucher.getVoucherCode());
                ps.setInt(2, voucher.getVoucherDiscount());
                ps.setTimestamp(3, Timestamp.valueOf(voucher.getVoucherValidFrom()));
                ps.setTimestamp(4, Timestamp.valueOf(voucher.getVoucherValidTo()));
                ps.setBoolean(5, voucher.getVoucherStatus());
                ps.setBoolean(6, voucher.getIsHidden());
                ps.setInt(7, voucher.getVoucherID());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Cập nhật voucher thành công!");
                    return true;
                } else {
                    System.out.println("Cập nhật voucher thất bại, không có dòng nào được thay đổi.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vi phạm ràng buộc dữ liệu: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật voucher: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // DELETE - Xóa voucher (soft delete)
    public boolean deleteVoucher(int voucherId) {
        String sql = "UPDATE Vouchers SET is_hidden = 1 WHERE voucher_id = ?";

        try (Connection conn = getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, voucherId);

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Xóa voucher thành công!");
                    return true;
                } else {
                    System.out.println("Xóa voucher thất bại, không có dòng nào được cập nhật.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa voucher: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // READ by ID - Lấy một voucher cụ thể theo voucher_id
    public Voucher getVoucherById(int voucherId) {
        String sql = "SELECT * FROM Vouchers WHERE voucher_id = ?";
        Voucher voucher = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voucherId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voucher = new Voucher();
                    voucher.setVoucherID(rs.getInt("voucher_id"));
                    voucher.setVoucherCode(rs.getString("voucher_code"));
                    voucher.setVoucherDiscount(rs.getInt("voucher_discount"));
                    voucher.setVoucherValidFrom(rs.getTimestamp("voucher_valid_from").toLocalDateTime());
                    voucher.setVoucherValidTo(rs.getTimestamp("voucher_valid_to").toLocalDateTime());
                    voucher.setVoucherStatus(rs.getBoolean("voucher_status"));
                    voucher.setIsHidden(rs.getBoolean("is_hidden"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voucher;
    }
}
