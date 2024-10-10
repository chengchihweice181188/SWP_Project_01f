package DAOs;

import Models.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Add cart item
    public void addCartItem(CartItem cartItem) throws SQLException {
        String sql = "INSERT INTO cart_items (user_id, product_id, product_option_id, quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cartItem.getUser_id());
        statement.setInt(2, cartItem.getProduct_id());
        statement.setInt(3, cartItem.getProduct_option_id());
        statement.setInt(4, cartItem.getQuantity());
        statement.executeUpdate();
    }

    // View cart item list for a user
    public List<CartItem> getCartItemsByUser(int userId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            CartItem cartItem = new CartItem(
                rs.getInt("cart_item_id"),
                rs.getInt("user_id"),
                rs.getInt("product_id"),
                rs.getInt("product_option_id"),
                rs.getInt("quantity")
            );
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    // Remove cart item
    public void removeCartItem(int cartItemId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cartItemId);
        statement.executeUpdate();
    }
}
