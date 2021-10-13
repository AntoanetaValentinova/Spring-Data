package com.jsonprocessing.demo.service.impl;

import com.google.gson.Gson;
import com.jsonprocessing.demo.constants.GlobalConst;
import com.jsonprocessing.demo.model.dto.CategoryCountProductsDto;
import com.jsonprocessing.demo.model.dto.CategorySeedDataDto;
import com.jsonprocessing.demo.model.entity.Category;
import com.jsonprocessing.demo.model.entity.Product;
import com.jsonprocessing.demo.repository.CategoryRepository;
import com.jsonprocessing.demo.service.CategoryService;
import com.jsonprocessing.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {
    public static final String CATEGORIES_PATH="categories.json";
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count()>0) {
            return;
        }
        CategorySeedDataDto[] categorySeedDataDtos =
                this.gson.fromJson(Files.readString(Path.of(GlobalConst.RESOURCES_FILES_PATH + CATEGORIES_PATH)),
                        CategorySeedDataDto[].class);

        Arrays.stream(categorySeedDataDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDataDto -> modelMapper.map(categorySeedDataDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> randomCategories=new HashSet<>();
        int randomNumberOfCategories= ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 0; i <randomNumberOfCategories ; i++) {
            long randomId=ThreadLocalRandom.current().nextLong(1,categoryRepository.count()+1);
            randomCategories.add(this.categoryRepository.findById(randomId).orElse(null));
        }
        return randomCategories;
    }

    @Override
    public List<CategoryCountProductsDto> findAllCategoriesOrderByTheirProductsCount() {

        List<CategoryCountProductsDto> categoryCountProductsDtos = this.categoryRepository.findAllByProductsCount()
                .stream()
                .map(category -> {
                    CategoryCountProductsDto categoryCountProductsDto = modelMapper.map(category, CategoryCountProductsDto.class);
                    categoryCountProductsDto.setAveragePrice(category.getProducts()
                            .stream()
                            .mapToDouble(product -> product.getPrice().doubleValue())
                            .average()
                            .orElse(0)
                    );
                    categoryCountProductsDto.setProductsCount(category.getProducts().size());
                    categoryCountProductsDto.setTotalRevenue(category
                            .getProducts()
                            .stream()
                            .mapToDouble(product->product.getPrice().doubleValue())
                            .sum()
                    );
                    return categoryCountProductsDto;
                })
                .collect(Collectors.toList());
        return  categoryCountProductsDtos;
    }
}
