package com.jsonprocessing.demo.repository;

import com.jsonprocessing.demo.model.entity.Category;
import com.jsonprocessing.demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c "+
    "ORDER BY c.products.size")
    List<Category> findAllByProductsCount();
}
