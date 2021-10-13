package com.jsonprocessing.demo;

import com.google.gson.Gson;
import com.jsonprocessing.demo.model.dto.CategoryCountProductsDto;
import com.jsonprocessing.demo.model.dto.ProductNamePriceSellerDto;
import com.jsonprocessing.demo.model.dto.UserProductsDto;
import com.jsonprocessing.demo.model.dto.UserWithSoldProductsDto;
import com.jsonprocessing.demo.model.entity.Category;
import com.jsonprocessing.demo.service.CategoryService;
import com.jsonprocessing.demo.service.ProductService;
import com.jsonprocessing.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    public static final String OUT_FOLDER_PATH="src\\main\\resources\\files\\out\\";
    public static final String PRODUCTS_IN_RANGE_PATH="products_in_range.json";
    public static final String USERS_WITH_SOLD_PRODUCTS_PATH="users_with_sold_products.json";
    public static final String CATEGORIES_BY_COUNT_OF_PRODUCTS_PATH="categories_by_count_of_products.json";
    public static final String USERS_WITH_PRODUCTS_PATH="users_with_products.json";

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Gson gson;
    private final Scanner scan;

    public CommandLineRunnerImpl(UserService userService, ProductService productService, CategoryService categoryService, Gson gson) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.gson = gson;
        this.scan = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Please, enter exercise number:");
        int exerciseNumber= Integer.parseInt(scan.nextLine());

        switch (exerciseNumber) {
            case 1: productsInRange(); break;
            case 2: successfullySoldProducts(); break;
            case 3: categoriesByCountOfProducts(); break;
            case 4: usersAndProducts(); break;
        }

    }

    private void usersAndProducts() throws IOException {
        UserProductsDto allUsersWithAtLeatOneSoldProductOrderByCountOfProductsAndLastName =
                this.userService.findAllUsersWithAtLeatOneSoldProductOrderByCountOfProductsAndLastName();
        String string = this.gson.toJson(allUsersWithAtLeatOneSoldProductOrderByCountOfProductsAndLastName);
        Files.writeString(Path.of(OUT_FOLDER_PATH+USERS_WITH_PRODUCTS_PATH),string);
    }

    private void categoriesByCountOfProducts() throws IOException {
        List<CategoryCountProductsDto> allCategoriesOrderByTheirProductsCount = this.categoryService.findAllCategoriesOrderByTheirProductsCount();
        String string = this.gson.toJson(allCategoriesOrderByTheirProductsCount);

        Files.writeString(Path.of(OUT_FOLDER_PATH+CATEGORIES_BY_COUNT_OF_PRODUCTS_PATH),string);
    }

    private void successfullySoldProducts() throws IOException {
        List<UserWithSoldProductsDto> userWithSoldProductsDtos=
                this.userService.findAllUsersWithAtLeastOneSoldProductOrderByLastNameAndFirstName();
        String string = this.gson.toJson(userWithSoldProductsDtos);

        Files.writeString(Path.of(OUT_FOLDER_PATH+USERS_WITH_SOLD_PRODUCTS_PATH),string);
    }

    private void productsInRange() throws IOException {
        Set<ProductNamePriceSellerDto> productNamePriceSellerDtos =
                this.productService.getProductsWithPriceInRangeWithNoBuyerOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));
        String string = this.gson.toJson(productNamePriceSellerDtos);
        Files.writeString(Path.of(OUT_FOLDER_PATH+PRODUCTS_IN_RANGE_PATH),string);
    }

    private void seedData() throws IOException {
       this.userService.seedUsers();
       this.categoryService.seedCategories();
       this.productService.seedProducts();
    }
}
