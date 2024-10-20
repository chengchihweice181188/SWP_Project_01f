package DAOs;

import DBConnection.DBConnection;
import Models.CartItem;
import Models.Option;
import Models.Product;
import Models.ProductOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    public boolean addCartItem(CartItem cartItem) {
        String checkQuery = "SELECT quantity FROM CartItems WHERE user_id = ? AND product_id = ? AND product_option_id = ?";
        String updateQuery = "UPDATE CartItems SET quantity = quantity + ? WHERE user_id = ? AND product_id = ? AND product_option_id = ?";
        String insertQuery = "INSERT INTO CartItems (user_id, product_id, product_option_id, quantity) VALUES (?, ?, ?, ?)";
        if(cartItem.getProduct_option_id() == 0){
            checkQuery = "SELECT quantity FROM CartItems WHERE user_id = ? AND product_id = ? AND product_option_id IS NULL";
            updateQuery = "UPDATE CartItems SET quantity = quantity + ? WHERE user_id = ? AND product_id = ? AND product_option_id IS NULL";
            insertQuery = "INSERT INTO CartItems (user_id, product_id, product_option_id, quantity) VALUES (?, ?, NULL, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                 PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                checkStmt.setInt(1, cartItem.getUser_id());
                checkStmt.setInt(2, cartItem.getProduct_id());

                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int currentQuantity = rs.getInt("quantity");
                    updateStmt.setInt(1, cartItem.getQuantity());
                    updateStmt.setInt(2, cartItem.getUser_id());
                    updateStmt.setInt(3, cartItem.getProduct_id());

                    return updateStmt.executeUpdate() > 0; 
                } else {
                    insertStmt.setInt(1, cartItem.getUser_id());
                    insertStmt.setInt(2, cartItem.getProduct_id());
                    insertStmt.setInt(3, cartItem.getQuantity());

                    return insertStmt.executeUpdate() > 0; 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                 PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                checkStmt.setInt(1, cartItem.getUser_id());
                checkStmt.setInt(2, cartItem.getProduct_id());
                checkStmt.setInt(3, cartItem.getProduct_option_id());

                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int currentQuantity = rs.getInt("quantity");
                    updateStmt.setInt(1, cartItem.getQuantity());
                    updateStmt.setInt(2, cartItem.getUser_id());
                    updateStmt.setInt(3, cartItem.getProduct_id());
                    updateStmt.setInt(4, cartItem.getProduct_option_id());

                    return updateStmt.executeUpdate() > 0; 
                } else {
                    insertStmt.setInt(1, cartItem.getUser_id());
                    insertStmt.setInt(2, cartItem.getProduct_id());
                    insertStmt.setInt(3, cartItem.getProduct_option_id());
                    insertStmt.setInt(4, cartItem.getQuantity());

                    return insertStmt.executeUpdate() > 0; 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    
    
    
    public ProductOption GetProOptionByOptionId(int productId, int optionId) {
        String selectQuery = "SELECT * FROM ProductOptions WHERE product_id = ? AND option_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, productId);
            selectStmt.setInt(2, optionId);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                ProductOption productOption = new ProductOption();
                productOption.setProduct_option_id(rs.getInt("product_option_id"));
                productOption.setProduct_id(rs.getInt("product_id"));
                productOption.setOption_id(rs.getInt("option_id"));
                return productOption;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  
    }
    
    public Option getOptionsByProductOptionId(int productOptionId) {
        Option options = new Option();
        String sql = "SELECT o.option_id, o.option_name " +
                     "FROM ProductOptions po " +
                     "JOIN Options o ON po.option_id = o.option_id " +
                     "WHERE po.product_option_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productOptionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                options.setOption_id(rs.getInt("option_id"));
                options.setOption_name(rs.getString("option_name"));
                options.setPrice_adjustment(rs.getDouble("price_adjustment"));
                options.setIs_hidden(rs.getInt("is_hidden"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }
    
    public void updateCartItem(int cartItemId, int productId, int newQuantity, int newOptionId) {
        String sql = "UPDATE cartitems SET quantity = ?, product_option_id = (SELECT product_option_id FROM productoptions WHERE product_id = ? AND option_id = ?) WHERE cart_item_id = ?";
        if (newOptionId == 0){
            sql = "UPDATE cartitems SET quantity = ? WHERE cart_item_id = ?";
            try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, newQuantity);     
                ps.setInt(2, cartItemId);          
                ps.executeUpdate();              
            } catch (SQLException e) {
                e.printStackTrace();              
            }
        }
        else{
            try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
               ps.setInt(1, newQuantity);     
               ps.setInt(2, productId);        
               ps.setInt(3, newOptionId);       
               ps.setInt(4, cartItemId);        
               ps.executeUpdate();              
           } catch (SQLException e) {
               e.printStackTrace();              
           }
        }
    }
    
    public List<Option> getOptionsByProductId(int productId) {
        List<Option> optionsList = new ArrayList<>();
        String sql = "SELECT o.option_id, o.option_name, o.price_adjustment, o.is_hidden " +
                     "FROM ProductOptions po " +
                     "JOIN Options o ON po.option_id = o.option_id " +
                     "WHERE po.product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua kết quả và thêm mỗi Option vào danh sách
            while (rs.next()) {
                Option option = new Option();
                option.setOption_id(rs.getInt("option_id"));
                option.setOption_name(rs.getString("option_name"));
                option.setPrice_adjustment(rs.getDouble("price_adjustment"));
                option.setIs_hidden(rs.getInt("is_hidden"));
                optionsList.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionsList;
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

    public void clearCart(int userId) {
        String query = "DELETE FROM CartItems WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
