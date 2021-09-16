package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    @Query
    List<Ingredient> findAllByNameStartingWith(String str);

    @Query
    List<Ingredient> findAllByNameInOrderByPrice(List<String> ingredients);

    @Query("DELETE FROM Ingredient i WHERE i.name=:name")
    @Modifying
    void deleteAllByName(String name);

    @Query("UPDATE Ingredient i SET i.price=i.price+i.price*0.1")
    @Modifying
    void increaseAllIngredientsPricesWith10Perc();

    @Query("UPDATE Ingredient i SET i.price=i.price+i.price*0.1 WHERE i.name IN :names")
    @Modifying
    void increaseIngredientsPricesByNameWith10Perc(List<String> names);

}
