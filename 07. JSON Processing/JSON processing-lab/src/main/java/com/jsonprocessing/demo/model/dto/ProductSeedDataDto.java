package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;
import com.jsonprocessing.demo.model.entity.Category;
import com.jsonprocessing.demo.model.entity.User;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public class ProductSeedDataDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductSeedDataDto() {
    }

    @Size(min=3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
