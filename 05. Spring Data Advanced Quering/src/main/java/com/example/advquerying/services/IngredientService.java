package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> getAllIngredientsByNameStartingWithString(String string);

    List<Ingredient> getAllIngredientsByNameIn(List<String> ingredients);

    void deleteIngredientByName(String name);

    void increaseAllIngredientPrices();

    void increasePricesByGivenName(List<String> names);
}
