package com.springdataautomapobj.demo.models.dto;

import java.math.BigDecimal;

public class GameAllViewDto {
    private String title;
    private BigDecimal price;

    public GameAllViewDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
