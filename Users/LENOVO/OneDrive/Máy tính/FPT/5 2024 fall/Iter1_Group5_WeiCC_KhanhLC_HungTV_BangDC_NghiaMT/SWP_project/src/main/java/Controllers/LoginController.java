/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.LoginDAO;
import Models.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Luu Chi Khanh-CE181175
 */
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String rem = request.getParameter("remember-me");
        String hashedPassword = MD5Hash(pass);
        // Kiểm tra tài khoản người dùng
        User acc = LoginDAO.Validate(user, hashedPassword);

        if (acc == null) { // Nếu tài khoản hoặc mật khẩu không đúng
            request.setAttribute("msg", "Invalid username or password!");
            // Quay lại trang login.jsp với thông báo lỗi
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Thiết lập cookie nếu người dùng chọn "Remember me"
            Cookie userCookie = new Cookie("username", user);
            Cookie passwordCookie = new Cookie("password", pass);
            Cookie rememberCookie = new Cookie("remember", rem);

            if (rem != null) { // Nếu chọn "Remember me"
                userCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
                passwordCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
                rememberCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
            } else {  // Nếu không chọn
                userCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                rememberCookie.setMaxAge(0);
            }

            // Lưu cookie vào trình duyệt
            response.addCookie(userCookie);
            response.addCookie(passwordCookie);
            response.addCookie(rememberCookie);

            // Lưu thông tin người dùng vào session
            HttpSession session = request.getSession();
            session.setAttribute("Users", acc);

            // Phân quyền dựa trên role_id
            int roleId = acc.getRole();

            RequestDispatcher dispatcher;
            if (roleId == 0 || roleId == 1) {
                // Nếu là admin, chuyển hướng đến trang quản lý
                response.sendRedirect("manageBar.jsp");
            } else {
                // Nếu là khách hàng, chuyển hướng đến trang chính
                response.sendRedirect("index.jsp");
            }
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
