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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Bang
 */
public class UpdatePromotionServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdatePromotionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePromotionServlet at " + request.getContextPath() + "</h1>");
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
            handlePromotionUpdate(request, response);
        } else if (type.equals("voucher")) {
            handleVoucherUpdate(request, response);
        } else {
            request.getSession().setAttribute("errorMessage", "Unknown request type.");
            response.sendRedirect(request.getContextPath() + "/Promotion");
        }
    }

    private void handlePromotionUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("promotionId"));
            int discount = Integer.parseInt(request.getParameter("promotionDiscount"));
            String validFromStr = request.getParameter("promotionValidFrom");
            String validToStr = request.getParameter("promotionValidTo");
            int productId = Integer.parseInt(request.getParameter("productId"));
            boolean isHidden = false;

            // Kiểm tra giá trị discount
            if (discount < 0 || discount > 100) {
                request.getSession().setAttribute("errorMessage", "Giảm giá phải từ 0 đến 100%");
                response.sendRedirect("/Promotion");
                return;
            }

            // Chuyển đổi chuỗi ngày giờ thành LocalDateTime với định dạng phù hợp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime validFrom = LocalDateTime.parse(validFromStr, formatter);
            LocalDateTime validTo = LocalDateTime.parse(validToStr, formatter);

            // Kiểm tra ngày
            if (validFrom.isAfter(validTo)) {
                request.getSession().setAttribute("errorMessage", "Ngày bắt đầu không được sau ngày kết thúc");
                response.sendRedirect("/Promotion");
                return;
            }

            Promotion promotion = new Promotion(id, discount, validFrom, validTo, productId, isHidden);
            PromotionDAO promotionDAO = new PromotionDAO();

            if (promotionDAO.updatePromotion(promotion)) {
                request.getSession().setAttribute("successMessage", "Cập nhật khuyến mãi thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Cập nhật khuyến mãi thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để kiểm tra
            request.getSession().setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage());
        }
        response.sendRedirect("/Promotion");

    }

    private void handleVoucherUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("voucherId"));
            String code = request.getParameter("voucherCode");
            int discount = Integer.parseInt(request.getParameter("voucherDiscount"));
            String validFromStr = request.getParameter("voucherValidFrom");
            String validToStr = request.getParameter("voucherValidTo");
            boolean voucherStatus = true; // or get from form if applicable
            boolean isHidden = false;

            // Validate discount
            if (discount < 0 || discount > 100) {
                request.getSession().setAttribute("errorMessage", "Giảm giá phải từ 0 đến 100%");
                response.sendRedirect(request.getContextPath() + "/Promotion");
                return;
            }

            // Parse date strings into LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime validFrom = LocalDateTime.parse(validFromStr, formatter);
            LocalDateTime validTo = LocalDateTime.parse(validToStr, formatter);

            // Validate dates
            if (validFrom.isAfter(validTo)) {
                request.getSession().setAttribute("errorMessage", "Ngày bắt đầu không được sau ngày kết thúc");
                response.sendRedirect(request.getContextPath() + "/Promotion");
                return;
            }

            // Create Voucher object
            Voucher voucher = new Voucher(id, code, discount, validFrom, validTo, voucherStatus, isHidden);
            VoucherDAO voucherDAO = new VoucherDAO();

            // Update voucher in database
            if (voucherDAO.updateVoucher(voucher)) {
                request.getSession().setAttribute("successMessage", "Cập nhật voucher thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Cập nhật voucher thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Dữ liệu không hợp lệ: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/Promotion");
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
