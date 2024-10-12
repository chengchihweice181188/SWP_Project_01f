/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.PromotionDAO;
import DAO.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Bang
 */
public class DeletePromotionServlet extends HttpServlet {

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
            out.println("<title>Servlet DeletePromotionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeletePromotionServlet at " + request.getContextPath() + "</h1>");
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
        String type = request.getParameter("type");
        if (type.equals("promotion")) {
            handleDeletePromotion(request, response);
        } else if (type.equals("voucher")) {
            handleDeleteVoucher(request, response);
        } else {
            request.getSession().setAttribute("errorMessage", "Unknown request type.");
            response.sendRedirect(request.getContextPath() + "/Promotion");
        }
    }

    protected void handleDeletePromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("promotionId"));
            PromotionDAO promotionDAO = new PromotionDAO();

            if (promotionDAO.deletePromotion(id)) {
                request.getSession().setAttribute("successMessage", "Xóa khuyến mãi thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Xóa khuyến mãi thất bại.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage());
        }
        response.sendRedirect("/Promotion");

    }

    protected void handleDeleteVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("VoucherId"));
            VoucherDAO voucherDAO = new VoucherDAO();

            if (voucherDAO.deleteVoucher(id)) {
                request.getSession().setAttribute("successMessage", "Xóa khuyến mãi thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Xóa khuyến mãi thất bại.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage());
        }
        response.sendRedirect("/Promotion");

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
