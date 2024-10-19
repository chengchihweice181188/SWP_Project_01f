/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Product {

    private int product_id;
    private String product_name;
    private String product_description;
    private double product_price;
    private String product_image;
    private int category_id;
    private int is_hidden;
    private List<Option> options;
    private String category_name;

    public Product(int product_id, String product_name, String product_description, double product_price, String product_image, int category_id, int is_hidden, List<Option> options, String category_name) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_image = product_image;
        this.category_id = category_id;
        this.is_hidden = is_hidden;
        this.options = options;
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    public Product() {
    }

    public Product(int product_id, String product_name, String product_description, double product_price, String product_image, int category_id, int is_hidden) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_image = product_image;
        this.category_id = category_id;
        this.is_hidden = is_hidden;
    }

    public Product(String product_name, String product_description, double product_price, String product_image, int category_id) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_image = product_image;
        this.category_id = category_id;
    }

    public Product(int product_id, String product_name, String product_description, double product_price, String product_image, int category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_image = product_image;
        this.category_id = category_id;
    }

    public Product(int product_id, String product_name, String product_description, double product_price, int category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.category_id = category_id;
    }

    
}
