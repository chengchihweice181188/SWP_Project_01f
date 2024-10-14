package Controllers;

import DAOs.CartItemDAO;
import Models.CartItem;
import Models.Product;  // Thêm Product model
import DTOs.CartSummary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CartItemController", urlPatterns = {"/CartItem"})
public class CartItemController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int userId = 1; 
        CartItemDAO cartItemDAO = new CartItemDAO();
        
        List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(userId);
        
        List<Product> products = new ArrayList<>();
        
        for (CartItem item : cartItems) {
            Product product = cartItemDAO.getProductById(item.getProduct_id());
            products.add(product); 
        }

        double originalPrice = 0.0;
        double discount = 0.0;
        double shippingFee = 20.0; 
        for (CartItem item : cartItems) {
            Product product = cartItemDAO.getProductById(item.getProduct_id());
            originalPrice += product.getProduct_price() * item.getQuantity();
        }
        double total = originalPrice - discount + shippingFee;

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("products", products); 
        request.setAttribute("cartSummary", new CartSummary(originalPrice, discount, shippingFee, total));
        
        request.getRequestDispatcher("/CartItem.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            CartItemDAO cartItemDAO = new CartItemDAO();

            cartItemDAO.deleteCartItem(cartItemId);
        if ("delete".equals(action)) {

            response.sendRedirect("CartItem");
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying Cart Items";
    }
}
