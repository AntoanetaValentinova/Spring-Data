package com.jsonprocessing.demo.service;

import com.jsonprocessing.demo.model.dto.CategoryCountProductsDto;
import com.jsonprocessing.demo.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

    List<CategoryCountProductsDto> findAllCategoriesOrderByTheirProductsCount();
}
