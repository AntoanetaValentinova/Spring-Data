package com.jsonprocessing.demo.model.dto;

import com.google.gson.annotations.Expose;
import com.jsonprocessing.demo.model.entity.Product;

import java.util.List;

public class UserFirstLastAgeSoldProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private ProductSoldDto soldProducts;

    public UserFirstLastAgeSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductSoldDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductSoldDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
