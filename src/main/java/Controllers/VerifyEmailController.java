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

@WebServlet(name = "VerifyEmailController", urlPatterns = {"/verifyEmail", "/resendCode"})
public class VerifyEmailController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/verifyEmail".equals(action)) {
            // Lấy mã xác thực từ request và session
            String verificationCode = request.getParameter("verificationCode");
            String sessionCode = (String) request.getSession().getAttribute("verificationCode");
            System.out.println("Mã xác thực người dùng nhập: " + verificationCode);
            System.out.println("Mã xác thực trong session: " + sessionCode);
            if (verificationCode != null && verificationCode.equals(sessionCode)) {
                String username = (String) request.getSession().getAttribute("username");
                String password = (String) request.getSession().getAttribute("password");
                String email = (String) request.getSession().getAttribute("email");

                // Đăng ký người dùng mới vào database
                RegisterDAO registerDAO = new RegisterDAO();
                String result = registerDAO.registerUser(username, password, email);

                if ("SUCCESS".equals(result)) {
                    response.sendRedirect("login.jsp?msg=Xác thực thành công, bạn có thể đăng nhập.");
                } else {
                    request.setAttribute("msg", "Đăng ký không thành công, vui lòng thử lại.");
                    request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "Mã xác thực không đúng hoặc đã hết hạn.");
                request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
            }
        } else if ("/resendCode".equals(action)) {
            // Gửi lại mã xác thực
            String email = (String) request.getSession().getAttribute("email");
            String newCode = generateVerificationCode();
            request.getSession().setAttribute("verificationCode", newCode);
            EmailService emailService = new EmailService();
            emailService.sendVerificationEmail(email, newCode);
            request.setAttribute("msg", "Mã xác thực mới đã được gửi.");
            request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
        }
    }

    private String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }
}

