/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Order {

    private int order_id;
    private String order_note;
    private double order_price;
    private String order_status;
    private Date order_date;
    private int payment_status;
    private String payment_method;
    private int feedback_rating;
    private String feedback_comment;
    private int user_id;
    private int staff_id;
    private int voucher_id;
    private String username; // dùng để khi lấy thuộc tính thuộc tính username bên bảng order thì vẫn có thuộc tính để gán vào.
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public int getFeedback_rating() {
        return feedback_rating;
    }

    public void setFeedback_rating(int feedback_rating) {
        this.feedback_rating = feedback_rating;
    }

    public String getFeedback_comment() {
        return feedback_comment;
    }

    public void setFeedback_comment(String feedback_comment) {
        this.feedback_comment = feedback_comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public Order() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Order(int order_id, String order_note, double order_price, String order_status, Date order_date, int payment_status, String payment_method, int feedback_rating, String feedback_comment, int user_id, int staff_id, int voucher_id, String username) {
        this.order_id = order_id;
        this.order_note = order_note;
        this.order_price = order_price;
        this.order_status = order_status;
        this.order_date = order_date;
        this.payment_status = payment_status;
        this.payment_method = payment_method;
        this.feedback_rating = feedback_rating;
        this.feedback_comment = feedback_comment;
        this.user_id = user_id;
        this.staff_id = staff_id;
        this.voucher_id = voucher_id;
        this.username = username;
    }
      
}
