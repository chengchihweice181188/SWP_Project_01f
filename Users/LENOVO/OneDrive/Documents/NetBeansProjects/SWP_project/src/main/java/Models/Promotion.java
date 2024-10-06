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
public class Promotion {

    private int promotion_id;
    private double promotion_discount;
    private Date promotion_valid_from;
    private Date promotion_valid_to;
    private int product_id;
    private int is_hidden;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public double getPromotion_discount() {
        return promotion_discount;
    }

    public void setPromotion_discount(double promotion_discount) {
        this.promotion_discount = promotion_discount;
    }

    public Date getPromotion_valid_from() {
        return promotion_valid_from;
    }

    public void setPromotion_valid_from(Date promotion_valid_from) {
        this.promotion_valid_from = promotion_valid_from;
    }

    public Date getPromotion_valid_to() {
        return promotion_valid_to;
    }

    public void setPromotion_valid_to(Date promotion_valid_to) {
        this.promotion_valid_to = promotion_valid_to;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    public Promotion() {
    }

    public Promotion(int promotion_id, double promotion_discount, Date promotion_valid_from, Date promotion_valid_to, int product_id, int is_hidden) {
        this.promotion_id = promotion_id;
        this.promotion_discount = promotion_discount;
        this.promotion_valid_from = promotion_valid_from;
        this.promotion_valid_to = promotion_valid_to;
        this.product_id = product_id;
        this.is_hidden = is_hidden;
    }

}
