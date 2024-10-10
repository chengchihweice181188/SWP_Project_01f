package Controllers;

import DAOs.CartItemDAO;
import DBConnection.DBConnection;
import Models.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CartItemServlet")
public class CartItemController extends HttpServlet {
    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {
        // Get database connection when the servlet is initialized
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            cartItemDAO = new CartItemDAO(connection);
        } else {
            throw new ServletException("Database connection failed.");
        }
    }

    // Handles the POST request to add or remove a cart item
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            addCartItem(request, response);
        } else if ("remove".equalsIgnoreCase(action)) {
            removeCartItem(request, response);
        }
    }

    // Handles the GET request to view cart items
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        viewCartItems(request, response);
    }

    // Add cart item
    private void addCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int productOptionId = Integer.parseInt(request.getParameter("productOptionId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartItem cartItem = new CartItem(0, userId, productId, productOptionId, quantity);
        try {
            cartItemDAO.addCartItem(cartItem);
            response.getWriter().write("Cart item added successfully!");
        } catch (SQLException e) {
            response.getWriter().write("Error adding cart item: " + e.getMessage());
        }
    }

    // View cart items by user
    private void viewCartItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        try {
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUser(userId);
            StringBuilder output = new StringBuilder();
            for (CartItem item : cartItems) {
                output.append("Cart Item ID: ")
                      .append(item.getCart_item_id())
                      .append(", Product ID: ")
                      .append(item.getProduct_id())
                      .append(", Quantity: ")
                      .append(item.getQuantity())
                      .append("<br>");
            }
            response.getWriter().write(output.toString());
        } catch (SQLException e) {
            response.getWriter().write("Error retrieving cart items: " + e.getMessage());
        }
    }

    // Remove cart item
    private void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

        try {
            cartItemDAO.removeCartItem(cartItemId);
            response.getWriter().write("Cart item removed successfully!");
        } catch (SQLException e) {
            response.getWriter().write("Error removing cart item: " + e.getMessage());
        }
    }
}
