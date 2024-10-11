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
public class Voucher {
    private int voucherID;
    private String voucherCode;
    private int voucherDiscount;
    private LocalDateTime voucherValidFrom;
    private LocalDateTime voucherValidTo;
    private Boolean voucherStatus;
    private Boolean isHidden;

    public Voucher() {
    }

    public Voucher(int voucherID, String voucherCode, int voucherDiscount, LocalDateTime voucherValidFrom, LocalDateTime voucherValidTo, Boolean voucherStatus, Boolean isHidden) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.voucherDiscount = voucherDiscount;
        this.voucherValidFrom = voucherValidFrom;
        this.voucherValidTo = voucherValidTo;
        this.voucherStatus = voucherStatus;
        this.isHidden = isHidden;
    }
    
    public Voucher(String voucherCode, int voucherDiscount, LocalDateTime voucherValidFrom, LocalDateTime voucherValidTo, Boolean voucherStatus, Boolean isHidden) {
        this.voucherCode = voucherCode;
        this.voucherDiscount = voucherDiscount;
        this.voucherValidFrom = voucherValidFrom;
        this.voucherValidTo = voucherValidTo;
        this.voucherStatus = voucherStatus;
        this.isHidden = isHidden;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }
    
    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(int voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    public LocalDateTime getVoucherValidFrom() {
        return voucherValidFrom;
    }

    public void setVoucherValidFrom(LocalDateTime voucherValidFrom) {
        this.voucherValidFrom = voucherValidFrom;
    }

    public LocalDateTime getVoucherValidTo() {
        return voucherValidTo;
    }

    public void setVoucherValidTo(LocalDateTime voucherValidTo) {
        this.voucherValidTo = voucherValidTo;
    }

    public Boolean getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(Boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

}
