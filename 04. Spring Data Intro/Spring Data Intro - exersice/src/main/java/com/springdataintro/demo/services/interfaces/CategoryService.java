package com.springdataintro.demo.services.interfaces;

import com.springdataintro.demo.models.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
