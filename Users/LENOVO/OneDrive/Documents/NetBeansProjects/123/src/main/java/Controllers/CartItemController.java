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
import Models.Option;
import Models.Product;
import Models.ProductOption;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        GetCartItem(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");
        String action_update = request.getParameter("action_update_all");
        if ("add".equalsIgnoreCase(action)) {
            AddCartItem(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
//            UpdateCartItem(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            DeleteCart(request, response);
        } else if("update_all".equalsIgnoreCase(action_update)) {
            UpdateCart(request, response);
        }
    }
    private void GetCartItem(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        try{
            HttpSession session = request.getSession();
            User acc = (User) session.getAttribute("Users");
            int uid = acc.getUser_id();
            double originalPrice = 0.0;
            double discount = 0.0;
            double shippingFee = 20.0; 
            CartItemDAO cartItemDAO = new CartItemDAO();
            String errorParam = request.getParameter("error");
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(uid);
            List<Option> options= new ArrayList<>();
            List<Product> products = new ArrayList<>();
            List<Option> avalivableOption = new ArrayList<>();
            for (CartItem item : cartItems) {
                Product product = cartItemDAO.getProductById(item.getProduct_id());
                products.add(product); 
                originalPrice += product.getProduct_price() * item.getQuantity();
                int productOptionId = item.getProduct_option_id();
                if ( productOptionId != 0 ){
                    Option option = cartItemDAO.getOptionsByProductOptionId(productOptionId);
                    options.add(option);
                }
                else{
                    Option op = new Option();
                    options.add(op);
                }
            }
            for (Product item : products) {
                avalivableOption = cartItemDAO.getOptionsByProductId(item.getProduct_id());
                item.setOptions(avalivableOption);
            }
            double total = originalPrice - discount + shippingFee;
                        ViewCategoryDAO dao = new ViewCategoryDAO();
            List<Category> categoryList = dao.getAllCategories();
            if(errorParam != null){
                request.setAttribute("error", errorParam);
            }
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("products", products); 
            request.setAttribute("allOptions", options);
            request.setAttribute("cartSummary", new CartSummary(originalPrice, discount, shippingFee, total));

            request.getRequestDispatcher("/CartItem.jsp").forward(request, response);
        }
        catch(Exception e){
            response.sendRedirect("/login.jsp"); 
        }
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
        try{
            CartItemDAO cartItemDAO = new CartItemDAO();
            HttpSession session = request.getSession();
            User acc = (User) session.getAttribute("Users");
            int uid = acc.getUser_id();
            int productId = Integer.parseInt(request.getParameter("productId"));
            int optionId = Integer.parseInt(request.getParameter("productOptionId"));
            int quantity = Integer.parseInt(request.getParameter("productQuantity"));
            ProductOption temp = cartItemDAO.GetProOptionByOptionId(productId, optionId);
            CartItem cartItem = null;
            if(temp == null){
                cartItem = new CartItem(0, uid, productId, 0, quantity);
            }
            else{
                cartItem = new CartItem(0, uid, productId, temp.getProduct_option_id(), quantity);
            }
            boolean isAdded = cartItemDAO.addCartItem(cartItem);

            if (isAdded) {
                response.sendRedirect("Cart"); 
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add item to cart.");
            }
        }
        catch(Exception e){
            response.sendRedirect("/login.jsp"); 
        }
    }
    
    private void UpdateCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int newOptionId = 0;
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("Users");
        int uid = acc.getUser_id();
        CartItemDAO cartItemDAO = new CartItemDAO();
        List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(uid);
        for (CartItem cartItem : cartItems) {
            int cartItemId = cartItem.getCart_item_id();
            int productId = cartItem.getProduct_id();
            String check = request.getParameter("option_" + cartItemId);
            int newQuantity = Integer.parseInt(request.getParameter("quantity_" + cartItemId));
            if(check != null){
                newOptionId = Integer.parseInt(check);
            }
            else {
                newOptionId = 0;
            }
            cartItemDAO.updateCartItem(cartItemId, productId, newQuantity, newOptionId);
        }
        response.sendRedirect("/Cart");
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
