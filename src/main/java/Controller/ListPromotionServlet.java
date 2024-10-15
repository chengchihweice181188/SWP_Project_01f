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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bang
 */
public class ListPromotionServlet extends HttpServlet {

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
            out.println("<title>Servlet ListPromotionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListPromotionServlet at " + request.getContextPath() + "</h1>");
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
        PromotionDAO promotionDAO = new PromotionDAO();
        List<Promotion> listPromotion = promotionDAO.getAllPromotions();
        request.setAttribute("promotions", listPromotion);

        VoucherDAO voucherDAO = new VoucherDAO();
        List<Voucher> listVoucher = voucherDAO.getAllVouchers();
        // Kiểm tra nếu có tham số tìm kiếm từ request
        String searchCode = request.getParameter("search");
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            // Lọc danh sách vouchers khớp với mã voucher nhập vào
            List<Voucher> matchedVouchers = new ArrayList<>();
            List<Voucher> unmatchedVouchers = new ArrayList<>();

            for (Voucher voucher : listVoucher) {
                if (voucher.getVoucherCode().equalsIgnoreCase(searchCode.trim())) {
                    matchedVouchers.add(voucher);
                } else {
                    unmatchedVouchers.add(voucher);
                }
            }
            // Đưa các voucher khớp lên đầu danh sách
            listVoucher.clear();
            listVoucher.addAll(matchedVouchers);
            listVoucher.addAll(unmatchedVouchers);
        }
        request.setAttribute("vouchers", listVoucher);
        // Chuyển tiếp request tới trang JSP để hiển thị
        request.getRequestDispatcher("/promotion.jsp").forward(request, response);

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
        processRequest(request, response);
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
