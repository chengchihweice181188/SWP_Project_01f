/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;
import DAOs.CartItemDAO;
import DAOs.ViewCategoryDAO;
import DTOs.CartSummary;
import Models.CartItem;
import Models.Category;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CartItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Display the cart
        GetCartItem(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            AddCartItem(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
//            UpdateCartItem(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            DeleteCart(request, response);
        }
    }
    private void GetCartItem(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
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
                    ViewCategoryDAO dao = new ViewCategoryDAO();
        List<Category> categoryList = dao.getAllCategories();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("products", products); 
        request.setAttribute("cartSummary", new CartSummary(originalPrice, discount, shippingFee, total));
        
        request.getRequestDispatcher("/CartItem.jsp").forward(request, response);
    }
    
    private void DeleteCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        CartItemDAO cartItemDAO = new CartItemDAO();
        cartItemDAO.deleteCartItem(cartItemId);
        response.sendRedirect("Cart");
    }
    
    private void AddCartItem(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        int productId = Integer.parseInt(request.getParameter("productId"));
//        int productOptionId = Integer.parseInt(request.getParameter("productOptionId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int userId = 1;
        int productId = Integer.parseInt(request.getParameter("productId"));
        int productOptionId = 2;
        int quantity = Integer.parseInt(request.getParameter("productQuantity"));
        CartItem cartItem = new CartItem(0, userId, productId, productOptionId, quantity);
        CartItemDAO cartItemDAO = new CartItemDAO();

        boolean isAdded = cartItemDAO.addCartItem(cartItem);

        if (isAdded) {
            response.sendRedirect("Cart"); 
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add item to cart.");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
