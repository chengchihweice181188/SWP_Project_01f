/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ManageAccountDAO;
import DAOs.ManageProductDAO;
import Models.Product;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ManageAccountController extends HttpServlet {

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
            out.println("<title>Servlet ManageAccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageAccountController at " + request.getContextPath() + "</h1>");
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
        String path = request.getRequestURI();
        if (path.equals("/ManageAccount")) {
            ManageAccountDAO dao = new ManageAccountDAO();
            //Đưa product list tạo ở DAO vào session
            List<User> userList = dao.getAllAccount();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("/viewAccountList.jsp").forward(request, response);
        }
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
        if (request.getParameter("btnChangeStatus") != null) {
            int user_id = Integer.parseInt(request.getParameter("txtUserId"));
            int user_status = Integer.parseInt(request.getParameter("txtUserStatus"));
            User obj = new User(user_id, user_status);
            ManageAccountDAO dao = new ManageAccountDAO();
            int count = dao.updateUserStatus(obj);
            if (count > 0) {
                String msg = "Cập nhật thành công.";
                request.getSession().setAttribute("msg", msg);
                response.sendRedirect("/ManageAccount");
            } else {
                String msg = "Cập nhật thất bại. Vui lòng thử lại.";
                request.getSession().setAttribute("msg", msg);
                response.sendRedirect("/ManageAccount");
            }
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
