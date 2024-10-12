package Controllers;

import DAOs.RegisterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterByGoogleServlet", urlPatterns = {"/RegisterByGoogle"})
public class RegisterByGoogleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("RegisterByGoogle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = (String) request.getSession().getAttribute("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        RegisterDAO registerDAO = new RegisterDAO();

        String result = registerDAO.registerUser(username, "", email, phone, address);
        if ("SUCCESS".equals(result)) {
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("msg", "Registration failed, please try again.");
            request.getRequestDispatcher("RegisterByGoogle.jsp").forward(request, response);
        }
    }
}
