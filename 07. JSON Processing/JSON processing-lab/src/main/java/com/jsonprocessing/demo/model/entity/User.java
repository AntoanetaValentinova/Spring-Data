package com.jsonprocessing.demo.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<User> friends;
    private Set<Product> productsSold;
    private Set<Product> productsBought;

    public User() {
        this.friends=new HashSet<>();
        this.productsSold=new HashSet<>();
        this.productsBought=new HashSet<>();
    }

    @Column(name="first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name",nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }


    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    public Set<Product> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(Set<Product> productsSold) {
        this.productsSold = productsSold;
    }

    @OneToMany(mappedBy = "buyer")
    public Set<Product> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(Set<Product> productsBought) {
        this.productsBought = productsBought;
    }
}
