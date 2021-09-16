package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> getAllShampoosBySizeOrderById(Size size);

    List<Shampoo> getAllShampoosBySizeOrLabelId(Size size,Long labelId);

    List<Shampoo> getAllShampoosWithPriceGreaterThanOrderByPrice(BigDecimal price);

    int getCountOfShampoosWithPriceLowerThanTheGiven(BigDecimal price);

    List<Shampoo> getAllShampoosByIngredientsNames(List<String> names);

    List<Shampoo> getAllShampoosWithCountOfIngredientsLessThanTheGiven(Long count);
}
