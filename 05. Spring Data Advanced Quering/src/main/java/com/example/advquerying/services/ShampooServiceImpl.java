package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService{
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> getAllShampoosBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> getAllShampoosBySizeOrLabelId(Size size, Long labelId) {
        return this.shampooRepository.findAllBySizeOrLabel_IdOrderByPrice(size,labelId);
    }

    @Override
    public List<Shampoo> getAllShampoosWithPriceGreaterThanOrderByPrice(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int getCountOfShampoosWithPriceLowerThanTheGiven(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> getAllShampoosByIngredientsNames(List<String> names) {
        return this.shampooRepository.findAllShampoosWithIngredients(names);
    }

    @Override
    public List<Shampoo> getAllShampoosWithCountOfIngredientsLessThanTheGiven(Long count) {
        return this.shampooRepository.findAllShampoosWithCountLessThanTheGiven(count);
    }
}
