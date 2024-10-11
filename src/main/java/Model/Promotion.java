/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author Bang
 */
public class Promotion {
    private int promotionID;
    private int promotionDiscount;
    private LocalDateTime promotionValidFrom;
    private LocalDateTime promotionValidTo;
    private int productId;
    private Boolean isHidden;
    private String productName;

    public Promotion(int promotionDiscount, LocalDateTime promotionValidFrom, LocalDateTime promotionValidTo, int productId, Boolean isHidden) {
        this.promotionDiscount = promotionDiscount;
        this.promotionValidFrom = promotionValidFrom;
        this.promotionValidTo = promotionValidTo;
        this.productId = productId;
        this.isHidden = isHidden;
    }

    public Promotion(int promotionID, int promotionDiscount, LocalDateTime promotionValidFrom, LocalDateTime promotionValidTo, int productId, Boolean isHidden) {
        this.promotionID = promotionID;
        this.promotionDiscount = promotionDiscount;
        this.promotionValidFrom = promotionValidFrom;
        this.promotionValidTo = promotionValidTo;
        this.productId = productId;
        this.isHidden = isHidden;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }
    
    public Promotion() {
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(int promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public LocalDateTime getPromotionValidFrom() {
        return promotionValidFrom;
    }

    public void setPromotionValidFrom(LocalDateTime promotionValidFrom) {
        this.promotionValidFrom = promotionValidFrom;
    }

    public LocalDateTime getPromotionValidTo() {
        return promotionValidTo;
    }

    public void setPromotionValidTo(LocalDateTime promotionValidTo) {
        this.promotionValidTo = promotionValidTo;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
     public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
