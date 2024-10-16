/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.UpdateProfileDAO;
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
public class UpdateProfileController extends HttpServlet {

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
            out.println("<title>Servlet UpdateProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileController at " + request.getContextPath() + "</h1>");
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

    // Hàm kiểm tra định dạng email
    private boolean validateEmail(String email) {
        // Kiểm tra email có chứa @
        if (email.contains("@")) {
            String[] parts = email.split("@");

            // Kiểm tra có đúng 1 dấu '@'
            if (parts.length == 2) {
                String localPart = parts[0];  // Phần trước dấu '@'
                String domainPart = parts[1]; // Phần sau dấu '@'

                // Kiểm tra phần trước dấu '@': không chứa dấu chấm liên tục và dấu chấm phải theo sau bởi chữ hoặc số
                if (!localPart.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*")) {
                    return false; // Nếu phần trước chứa dấu chấm không hợp lệ
                }

                // Kiểm tra phần sau dấu '@' phải bắt đầu bằng 'gmail' và có tên miền ít nhất 2 ký tự
                if (domainPart.matches("^gmail\\.[a-zA-Z]{2,}$")) {
                    return true; // Email hợp lệ
                }
            }
        }
        return false; // Email không hợp lệ
    }

    // Hàm kiểm tra định dạng số điện thoại mà không cần sử dụng Pattern
    private boolean validatePhone(String phone) {
        // Kiểm tra độ dài 10-11 số và bắt đầu bằng số 0
        if (phone.startsWith("0") && (phone.length() == 10 || phone.length() == 11)) {
            // Kiểm tra xem tất cả các ký tự còn lại có phải là số
            try {
                Long.parseLong(phone); // Chuyển chuỗi thành số
                return true;
            } catch (NumberFormatException e) {
                return false; // Không phải là số hợp lệ
            }
        }
        return false; // Số điện thoại không hợp lệ
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Lấy đối tượng user từ session
        User user = (User) session.getAttribute("Users");
        // Lấy thông tin từ form
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        // Kiểm tra xem user có null không
        if (user != null) {
            // Kiểm tra email hợp lệ
            if (!validateEmail(email)) {
                request.setAttribute("messageEmail", "Email không hợp lệ. Vui lòng nhập lại email đúng định dạng.");
                request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
                return; // Dừng xử lý tiếp khi email không hợp lệ
            }
            // Kiểm tra số điện thoại hợp lệ
            if (!validatePhone(phone)) {
                request.setAttribute("messagePhone", "Số điện thoại không hợp lệ. Vui lòng chỉ nhập số từ 10-11 số.");
                request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
                return; // Dừng xử lý tiếp khi số điện thoại không hợp lệ
            }
            // Cập nhật thông tin mới cho user hiện tại
            user.setEmail(email);
            user.setPhone_number(phone);
            user.setAddress(address);
            UpdateProfileDAO uPD = new UpdateProfileDAO();
            try {
                // Cập nhật thông tin người dùng trong cơ sở dữ liệu
                boolean isUpdated = uPD.updateAccount(user);
                if (isUpdated) {
                    // Nếu cập nhật thành công, cập nhật lại session
                    session.setAttribute("Users", user); // Cập nhật lại session với đối tượng user đã sửa
                    request.setAttribute("messageS", "Cập nhật thành công!");
                } else {
                    request.setAttribute("messageE", "Cập nhật thất bại!");
                    request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                // Lỗi kết nối cơ sở dữ liệu
                request.setAttribute("messageE", "Lỗi kết nối cơ sở dữ liệu! Vui lòng thử lại sau.");
                Logger.getLogger(UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Chuyển hướng đến trang Profile.jsp để xem kết quả
        request.getRequestDispatcher("profile.jsp").forward(request, response);
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
