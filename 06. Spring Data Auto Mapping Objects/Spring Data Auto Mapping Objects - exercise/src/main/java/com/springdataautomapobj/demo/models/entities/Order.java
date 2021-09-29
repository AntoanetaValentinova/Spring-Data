package com.springdataautomapobj.demo.models.entities;

import jdk.jfr.Enabled;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="orders")
public class Order extends BaseEntity{
    private User user;
    private Set<Game> products;

    public Order() {
    }

    public Order(User user, Set<Game> products) {
        this.user = user;
        this.products = new LinkedHashSet<>();
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany
    public Set<Game> getProducts() {
        return products;
    }

    public void setProducts(Set<Game> products) {
        this.products = products;
    }
}
