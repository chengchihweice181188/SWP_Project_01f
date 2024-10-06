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
public class Voucher {

    private int voucher_id;
    private String voucher_code;
    private double voucher_discount;
    private Date voucher_valid_from;
    private Date voucher_valid_to;
    private int voucher_status;
    private int is_hidden;

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public double getVoucher_discount() {
        return voucher_discount;
    }

    public void setVoucher_discount(double voucher_discount) {
        this.voucher_discount = voucher_discount;
    }

    public Date getVoucher_valid_from() {
        return voucher_valid_from;
    }

    public void setVoucher_valid_from(Date voucher_valid_from) {
        this.voucher_valid_from = voucher_valid_from;
    }

    public Date getVoucher_valid_to() {
        return voucher_valid_to;
    }

    public void setVoucher_valid_to(Date voucher_valid_to) {
        this.voucher_valid_to = voucher_valid_to;
    }

    public int getVoucher_status() {
        return voucher_status;
    }

    public void setVoucher_status(int voucher_status) {
        this.voucher_status = voucher_status;
    }

    public int getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    public Voucher() {
    }

    public Voucher(int voucher_id, String voucher_code, double voucher_discount, Date voucher_valid_from, Date voucher_valid_to, int voucher_status, int is_hidden) {
        this.voucher_id = voucher_id;
        this.voucher_code = voucher_code;
        this.voucher_discount = voucher_discount;
        this.voucher_valid_from = voucher_valid_from;
        this.voucher_valid_to = voucher_valid_to;
        this.voucher_status = voucher_status;
        this.is_hidden = is_hidden;
    }

}
