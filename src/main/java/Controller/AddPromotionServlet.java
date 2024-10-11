/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.PromotionDAO;
import DAO.VoucherDAO;
import Model.Promotion;
import Model.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Bang
 */
public class AddPromotionServlet extends HttpServlet {

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
            out.println("<title>Servlet AddPromotionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPromotionServlet at " + request.getContextPath() + "</h1>");
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
        String promotionType = request.getParameter("promotionType");

        try {
            if ("promotion".equalsIgnoreCase(promotionType)) {
                // Xử lý thêm mới Promotion
                int discount = Integer.parseInt(request.getParameter("promotionDiscount"));
                LocalDateTime validFrom = LocalDateTime.parse(request.getParameter("promotionValidFrom"));
                LocalDateTime validTo = LocalDateTime.parse(request.getParameter("promotionValidTo"));
                int productId = Integer.parseInt(request.getParameter("productId"));

                Promotion promotion = new Promotion(discount, validFrom, validTo, productId, false);
                PromotionDAO promotionDAO = new PromotionDAO();

                if (promotionDAO.createPromotion(promotion)) {
                    request.getSession().setAttribute("successMessage", "Thêm khuyến mãi thành công!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Thêm khuyến mãi thất bại.");
                }
            } else if ("voucher".equalsIgnoreCase(promotionType)) {
                // Xử lý thêm mới Voucher
                String voucherCode = request.getParameter("voucherCode");
                int discount = Integer.parseInt(request.getParameter("voucherDiscount"));
                LocalDateTime validFrom = LocalDateTime.parse(request.getParameter("voucherValidFrom"));
                LocalDateTime validTo = LocalDateTime.parse(request.getParameter("voucherValidTo"));

                Voucher voucher = new Voucher(voucherCode, discount, validFrom, validTo, true, false);
                VoucherDAO voucherDAO = new VoucherDAO();

                if (voucherDAO.createVoucher(voucher)) {
                    request.getSession().setAttribute("successMessage", "Thêm voucher thành công!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Thêm voucher thất bại.");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "Loại không hợp lệ.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage());
        }

        // Redirect lại trang danh sách hoặc trang tạo mới tùy yêu cầu
        response.sendRedirect("Promotion");
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
