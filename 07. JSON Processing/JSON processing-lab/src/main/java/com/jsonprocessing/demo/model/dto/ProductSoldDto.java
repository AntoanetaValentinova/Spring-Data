package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;
import com.jsonprocessing.demo.model.entity.Product;

import java.util.List;

public class ProductSoldDto {
    @Expose
    private Integer count;
    @Expose
    private List<ProductNamePriceDto> products;

    public ProductSoldDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductNamePriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNamePriceDto> products) {
        this.products = products;
    }
}


