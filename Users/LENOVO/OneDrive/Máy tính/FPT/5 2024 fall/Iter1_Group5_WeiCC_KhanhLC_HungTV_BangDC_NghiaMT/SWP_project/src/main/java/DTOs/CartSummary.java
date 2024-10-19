/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author LENOVO
 */
public class CartSummary {
    private double originalPrice;
    private double discount;
    private double shippingFee;
    private double total;

    // Constructor and getters/setters
    public CartSummary(double originalPrice, double discount, double shippingFee, double total) {
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.shippingFee = shippingFee;
        this.total = total;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public double getTotal() {
        return total;
    }
}
