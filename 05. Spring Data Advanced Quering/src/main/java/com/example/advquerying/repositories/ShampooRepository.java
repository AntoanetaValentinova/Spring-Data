package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {

    @Query
    List<Shampoo> findAllBySizeOrderById(Size size);

    @Query
    List<Shampoo> findAllBySizeOrLabel_IdOrderByPrice(Size size,Long labelId);

    @Query
    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    @Query
    int countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s JOIN s.ingredients AS i WHERE i.name IN :names" )
    List<Shampoo> findAllShampoosWithIngredients(List<String> names);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i GROUP BY s.id HAVING COUNT(i.id) < :count")
    List<Shampoo> findAllShampoosWithCountLessThanTheGiven(Long count);
}
