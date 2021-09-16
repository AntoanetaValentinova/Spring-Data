package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAllIngredientsByNameStartingWithString(String string) {
        return this.ingredientRepository.findAllByNameStartingWith(string);
    }

    @Override
    public List<Ingredient> getAllIngredientsByNameIn(List<String> ingredients) {
        return this.ingredientRepository.findAllByNameInOrderByPrice(ingredients);
    }

    @Override
    @Transactional
    public void deleteIngredientByName(String name) {
        this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void increaseAllIngredientPrices() {
        this.ingredientRepository.increaseAllIngredientsPricesWith10Perc();
    }

    @Override
    @Transactional
    public void increasePricesByGivenName(List<String> names) {
        this.ingredientRepository.increaseIngredientsPricesByNameWith10Perc(names);
    }
}
