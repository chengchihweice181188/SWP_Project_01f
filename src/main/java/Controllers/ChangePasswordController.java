/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ChangePasswordDAO;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class ChangePasswordController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePasswordController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("Users");
        // Lấy mật khẩu cũ, mật khẩu mới và xác nhận mật khẩu từ form
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra xem mật khẩu cũ có khớp với mật khẩu trong hệ thống không
        ChangePasswordDAO changePQD = new ChangePasswordDAO();
        boolean isOldPasswordCorrect = false;
        try {
            isOldPasswordCorrect = changePQD.checkPassword(currentUser.getUsername(), oldPassword);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!isOldPasswordCorrect) {
            // Nếu mật khẩu cũ không đúng, trả về trang đổi mật khẩu với thông báo lỗi
            request.setAttribute("errorMessage", "Mật khẩu cũ không đúng.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ dài mật khẩu mới phải >= 6 ký tự
        if (newPassword.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu phải chứa ít nhất một chữ hoa, một chữ thường, một số và một ký tự đặc biệt
        if (!newPassword.matches(".*[A-Z].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*[a-z].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*\\d.*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            // Nếu mật khẩu mới và xác nhận mật khẩu không khớp
            request.setAttribute("errorMessage", "Xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        try {
            // Cập nhật mật khẩu mới
            changePQD.updatePassword(currentUser.getUsername(), newPassword);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Sau khi cập nhật mật khẩu, chuyển hướng về trang thành công
        request.setAttribute("successMessage", "Cập nhật mật khẩu thành công.");
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
