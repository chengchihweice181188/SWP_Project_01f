package DAOs;

import DBConnection.DBConnection;
import Models.CartItem;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    public boolean addCartItem(CartItem cartItem) {
        String query = "INSERT INTO CartItems (user_id, product_id, product_option_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, cartItem.getUser_id());
            stmt.setInt(2, cartItem.getProduct_id());
            stmt.setInt(3, cartItem.getProduct_option_id());
            stmt.setInt(4, cartItem.getQuantity());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CartItem getCartItemById(int cart_item_id) {
        String query = "SELECT * FROM CartItems WHERE cart_item_id = 3";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

//            stmt.setInt(1, cart_item_id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Executed Query: " + stmt.toString());
            if (rs.next()) {
                return new CartItem(
                    rs.getInt("cart_item_id"),
                    rs.getInt("user_id"),
                    rs.getInt("product_id"),
                    rs.getInt("product_option_id"),
                    rs.getInt("quantity")
                );
            } else {
                System.out.println("No cart item found with id: " + cart_item_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    
    public double getProductPriceById(int productId) {
        double price = 0.0;
        String sql = "SELECT price FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    public Product getProductById(int productId) {
        Product product = null;  

        String sql = "SELECT product_id, product_name, product_description, product_price, product_image, category_id, is_hidden FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Tạo một đối tượng Product và gán giá trị từ ResultSet
                    product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_description"),
                        rs.getDouble("product_price"),
                        rs.getString("product_image"),
                        rs.getInt("category_id"),
                        rs.getInt("is_hidden")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;  
    }
    
    public List<CartItem> getCartItemsByUserId(int user_id) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM CartItems WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cartItems.add(new CartItem(
                    rs.getInt("cart_item_id"),
                    rs.getInt("user_id"),
                    rs.getInt("product_id"),
                    rs.getInt("product_option_id"),
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    
    public boolean updateCartItem(CartItem cartItem) {
        String query = "UPDATE CartItems SET user_id = ?, product_id = ?, product_option_id = ?, quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, cartItem.getUser_id());
            stmt.setInt(2, cartItem.getProduct_id());
            stmt.setInt(3, cartItem.getProduct_option_id());
            stmt.setInt(4, cartItem.getQuantity());
            stmt.setInt(5, cartItem.getCart_item_id());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCartItem(int cart_item_id) {
        String query = "DELETE FROM CartItems WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, cart_item_id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
