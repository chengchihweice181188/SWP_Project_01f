/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Bang
 */
public class Category {

    private String categoryName;
    private Boolean isHidden;
    private int categoryId;

    public Category() {
    }

    public Category(String categoryName, Boolean isHidden) {
        this.categoryName = categoryName;
        this.isHidden = isHidden;
    }

    public Category(String categoryName, Boolean isHidden, int categoryId) {
        this.categoryName = categoryName;
        this.isHidden = isHidden;
        this.categoryId = categoryId;
    }

  
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

}
