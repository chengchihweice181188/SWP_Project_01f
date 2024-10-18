package Controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */



import DAOs.ResetPasswordDAO;
import Services.EmailService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class ForgetPasswordController extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // Chuyển hướng đến trang login.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("forgetPassword.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        ResetPasswordDAO rPD = new ResetPasswordDAO();
        try {
            boolean emailExists = rPD.checkEmailExists(email);

            if (!emailExists) {
                request.setAttribute("errorMessage", "Email không tồn tại!");
                request.getRequestDispatcher("forgetPassword.jsp").forward(request, response);
            } else {
                int verificationCode = (int) (Math.random() * 900000) + 100000;
                String verificationCodeStr = String.valueOf(verificationCode);
                EmailService emailService = new EmailService();
                emailService.sendVerificationEmail(email, verificationCodeStr);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("verificationCode", verificationCodeStr);
            }
        } catch (Exception e) {
        }

        response.sendRedirect("verifyEmailRSP.jsp");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
