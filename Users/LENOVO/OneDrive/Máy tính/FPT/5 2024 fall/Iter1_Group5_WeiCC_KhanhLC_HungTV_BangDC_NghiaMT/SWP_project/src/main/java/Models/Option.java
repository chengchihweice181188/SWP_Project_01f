/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author LENOVO
 */
public class Option {

    private int option_id;
    private String option_name;
    private double price_adjustment;
    private int is_hidden;

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public double getPrice_adjustment() {
        return price_adjustment;
    }

    public void setPrice_adjustment(double price_adjustment) {
        this.price_adjustment = price_adjustment;
    }

    public int getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    public Option() {
    }

    public Option(int option_id, String option_name, double price_adjustment, int is_hidden) {
        this.option_id = option_id;
        this.option_name = option_name;
        this.price_adjustment = price_adjustment;
        this.is_hidden = is_hidden;
    }

}
