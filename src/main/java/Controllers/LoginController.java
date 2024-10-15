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
import java.sql.SQLException;

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
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("index.jsp");
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
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String rem = request.getParameter("remember-me");

        try {
            // Bắt đầu khối try để bắt lỗi từ phương thức Validate
            User acc = LoginDAO.Validate(user, pass);

            if (acc == null) { // nhập sai tài khoản hoặc mật khẩu
                request.setAttribute("msg", "Tên đăng nhập hoặc mật khẩu không hợp lệ!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Thiết lập cookie với tên người dùng (ví dụ)
                Cookie userCookie = new Cookie("username", user);
                Cookie passwordCookie = new Cookie("password", pass);
                Cookie rememberCookie = new Cookie("remember", rem);

                if (rem != null) { // chọn remember me
                    userCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
                    passwordCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
                    rememberCookie.setMaxAge(1 * 24 * 60 * 60); // 1 ngày
                } else {  // không chọn remember me
                    userCookie.setMaxAge(0);
                    passwordCookie.setMaxAge(0);
                    rememberCookie.setMaxAge(0);
                }
                // lưu vào browser
                response.addCookie(userCookie);
                response.addCookie(passwordCookie);
                response.addCookie(rememberCookie);

                // Lưu đối tượng acc vào session
                HttpSession session = request.getSession();
                session.setAttribute("Users", acc);

                // Phân quyền dựa trên role_id
                RequestDispatcher dispatcher;
                int roleId = acc.getRole(); // Lấy role_id từ đối tượng Account

                if (roleId == 0 || roleId == 1) {
                    // Admin
                    dispatcher = request.getRequestDispatcher("manageBar.jsp");
                } else {
                    // Customer
                    dispatcher = request.getRequestDispatcher("index.jsp");
                }
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            // Bắt lỗi kết nối hoặc lỗi từ cơ sở dữ liệu
            request.setAttribute("msg", "Lỗi kết nối cơ sở dữ liệu. Vui lòng thử lại sau!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            // Bắt các lỗi khác
            request.setAttribute("msg", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
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
