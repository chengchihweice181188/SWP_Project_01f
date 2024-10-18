/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ResetPasswordDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class ResetPasswordController extends HttpServlet {

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
            out.println("<title>Servlet ResetPasswordController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordController at " + request.getContextPath() + "</h1>");
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
        // Chuyển hướng đến trang login.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("resetPassword.jsp");
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
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");
        ResetPasswordDAO rPD = new ResetPasswordDAO();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");  // Lấy email từ session

        // Kiểm tra độ dài mật khẩu mới phải >= 6 ký tự
        if (newPassword.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu phải chứa ít nhất một chữ hoa, một chữ thường, một số và một ký tự đặc biệt
        if (!newPassword.matches(".*[A-Z].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*[a-z].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*\\d.*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            request.setAttribute("errorMessage", "Mật khẩu phải chứa ít nhất một chữ hoa, thường, số và 1 kí tự đặc biệt.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        boolean isSuccess = false;
        try {
            // Mã hóa mật khẩu mới trước khi cập nhật
            String newPasswordHash = MD5Hash(newPassword);
            isSuccess = rPD.updatePasswordByEmail(email, newPasswordHash);
        } catch (Exception ex) {
            Logger.getLogger(ResetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Kiểm tra kết quả cập nhật mật khẩu
        if (isSuccess) {
            // Nếu thành công, chuyển hướng đến trang đăng nhập với thông báo thành công
            session.invalidate();  // Xóa session khi quá trình hoàn tất
            // Nếu không thành công, hiển thị thông báo lỗi
            request.setAttribute("successMessage", "Đổi mật khẩu thành công.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            // Nếu không thành công, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }
    private String MD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
