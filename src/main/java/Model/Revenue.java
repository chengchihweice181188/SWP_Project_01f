/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Bang
 */
public class Revenue {

    private int revenueID;
    private int month;
    private int year;
    private int totalRevenue;

    public Revenue(int revenueID, int month, int year, int totalRevenue) {
        this.revenueID = revenueID;
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    public int getRevenueID() {
        return revenueID;
    }

    public void setRevenueID(int revenueID) {
        this.revenueID = revenueID;
    }

    public Revenue() {
    }

    public Revenue(int month, int year, int totalRevenue) {
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}
