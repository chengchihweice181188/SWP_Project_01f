/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import Services.EmailService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {"/Register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        RegisterDAO registerDAO = new RegisterDAO();
        if (registerDAO.isUserExists(username)) {
            request.setAttribute("msg", "Tên người dùng đã tồn tại, vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        } else if (registerDAO.isEmailExists(email)) {
            request.setAttribute("msg", "Email đã tồn tại, vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;

        }

        int verificationCode = (int) (Math.random() * 900000) + 100000;
        String verificationCodeStr = String.valueOf(verificationCode);
        EmailService emailService = new EmailService();
        emailService.sendVerificationEmail(email, verificationCodeStr);
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password", password);
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("verificationCode", verificationCodeStr);

        response.sendRedirect("verifyEmail.jsp");
    }
}
