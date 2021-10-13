package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoryCountProductsDto {
    @Expose
    private String category;
    @Expose
    private Integer productsCount;
    @Expose
    private BigDecimal averagePrice;
    @Expose
    private BigDecimal totalRevenue;

    public CategoryCountProductsDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = BigDecimal.valueOf(averagePrice);
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = BigDecimal.valueOf(totalRevenue);
    }
}
