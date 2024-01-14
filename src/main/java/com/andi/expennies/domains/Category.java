package com.andi.expennies.domains;

public class Category {
    private Integer categoryId;
    private Integer userId;
    private String title;
    private Double totalExpenses;

    public Category(Integer categoryId, Integer userId, String title, Double totalExpenses) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.title = title;
        this.totalExpenses = totalExpenses;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
