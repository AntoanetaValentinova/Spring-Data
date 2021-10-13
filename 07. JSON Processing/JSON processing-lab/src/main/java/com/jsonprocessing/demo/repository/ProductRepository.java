package com.jsonprocessing.demo.repository;

import com.jsonprocessing.demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Set<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
