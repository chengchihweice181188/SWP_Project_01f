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
import Model.Promotion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends DatabaseConnection {
    // READ - Lấy tất cả categories

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT p.*, pr.product_name FROM Promotions p JOIN Products pr ON p.product_id = pr.product_id WHERE p.is_hidden = 0";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionID(rs.getInt("promotion_id"));
                promotion.setPromotionDiscount(rs.getInt("promotion_discount"));

                Timestamp validFromTimestamp = rs.getTimestamp("promotion_valid_from");
                if (validFromTimestamp != null) {
                    promotion.setPromotionValidFrom(validFromTimestamp.toLocalDateTime());
                }

                Timestamp validToTimestamp = rs.getTimestamp("promotion_valid_to");
                if (validToTimestamp != null) {
                    promotion.setPromotionValidTo(validToTimestamp.toLocalDateTime());
                }

                promotion.setProductId(rs.getInt("product_id"));
                promotion.setProductName(rs.getString("product_name"));
                promotion.setIsHidden(rs.getBoolean("is_hidden"));
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }
// CREATE - Thêm một promotion mới

    public boolean createPromotion(Promotion promotion) {
        String sql = "INSERT INTO Promotions (promotion_discount, promotion_valid_from, promotion_valid_to, product_id, is_hidden) VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = getConnection()) {
            // Kiểm tra kết nối trước khi tiếp tục
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            // Tạo PreparedStatement và thực hiện câu lệnh INSERT
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, promotion.getPromotionDiscount());
                ps.setTimestamp(2, Timestamp.valueOf(promotion.getPromotionValidFrom()));
                ps.setTimestamp(3, Timestamp.valueOf(promotion.getPromotionValidTo()));
                ps.setInt(4, promotion.getProductId());
                ps.setBoolean(5, promotion.getIsHidden());

                int affectedRows = ps.executeUpdate();

                // Kiểm tra số lượng dòng bị ảnh hưởng để xác nhận INSERT thành công
                if (affectedRows > 0) {
                    System.out.println("Thêm khuyến mãi thành công!");
                    return true;
                } else {
                    System.out.println("Thêm khuyến mãi thất bại, không có dòng nào được chèn.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vi phạm ràng buộc dữ liệu (ví dụ: product_id không tồn tại): " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updatePromotion(Promotion promotion) {
        String sql = "UPDATE Promotions SET promotion_discount = ?, promotion_valid_from = ?, promotion_valid_to = ?, product_id = ?, is_hidden = ? WHERE promotion_id = ?";

        try ( Connection conn = getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, promotion.getPromotionDiscount());
                ps.setTimestamp(2, Timestamp.valueOf(promotion.getPromotionValidFrom()));
                ps.setTimestamp(3, Timestamp.valueOf(promotion.getPromotionValidTo()));
                ps.setInt(4, promotion.getProductId());
                ps.setBoolean(5, promotion.getIsHidden());
                ps.setInt(6, promotion.getPromotionID());

                System.out.println("Executing SQL: " + ps.toString());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Cập nhật khuyến mãi thành công!");
                    return true;
                } else {
                    System.out.println("Cập nhật khuyến mãi thất bại, không có dòng nào được thay đổi.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vi phạm ràng buộc dữ liệu: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletePromotion(int promotionID) {
        String sql = "UPDATE Promotions SET is_hidden = 1 WHERE promotion_id = ?";

        try ( Connection conn = getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.out.println("Kết nối không được thiết lập hoặc đã bị đóng.");
                return false;
            }

            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, promotionID);

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Xóa khuyến mãi thành công!");
                    return true;
                } else {
                    System.out.println("Xóa khuyến mãi thất bại, không có dòng nào được cập nhật.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Promotion getPromotionById(int promotionID) {
        String sql = "SELECT * FROM Promotions WHERE promotion_id = ?";
        Promotion promotion = null;

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    promotion = new Promotion();
                    promotion.setPromotionID(rs.getInt("promotion_id"));
                    promotion.setPromotionDiscount(rs.getInt("promotion_discount"));

                    Timestamp validFromTimestamp = rs.getTimestamp("promotion_valid_from");
                    if (validFromTimestamp != null) {
                        promotion.setPromotionValidFrom(validFromTimestamp.toLocalDateTime());
                    }

                    Timestamp validToTimestamp = rs.getTimestamp("promotion_valid_to");
                    if (validToTimestamp != null) {
                        promotion.setPromotionValidTo(validToTimestamp.toLocalDateTime());
                    }

                    promotion.setProductId(rs.getInt("product_id"));
                    promotion.setIsHidden(rs.getBoolean("is_hidden"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotion;
    }

}
