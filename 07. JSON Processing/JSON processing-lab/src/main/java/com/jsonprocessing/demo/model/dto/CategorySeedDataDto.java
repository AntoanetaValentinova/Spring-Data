package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class CategorySeedDataDto {
    @Expose
    private String name;

    public CategorySeedDataDto() {
    }

    @Size(min=3,max=15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
