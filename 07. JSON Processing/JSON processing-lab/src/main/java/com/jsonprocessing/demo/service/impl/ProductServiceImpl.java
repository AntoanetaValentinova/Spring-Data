package com.jsonprocessing.demo.service.impl;

import com.google.gson.Gson;
import com.jsonprocessing.demo.model.dto.ProductNamePriceSellerDto;
import com.jsonprocessing.demo.model.dto.ProductSeedDataDto;
import com.jsonprocessing.demo.model.entity.Product;
import com.jsonprocessing.demo.repository.ProductRepository;
import com.jsonprocessing.demo.service.CategoryService;
import com.jsonprocessing.demo.service.ProductService;
import com.jsonprocessing.demo.service.UserService;
import com.jsonprocessing.demo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jsonprocessing.demo.constants.GlobalConst.RESOURCES_FILES_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCTS_PATH="products.json";
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(UserService userService, CategoryService categoryService, ProductRepository productRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count()>0) {
            return;
        }
        ProductSeedDataDto[] productSeedDataDtos =
                this.gson.fromJson(Files.readString(Path.of(RESOURCES_FILES_PATH + PRODUCTS_PATH)),
                        ProductSeedDataDto[].class);

        Arrays.stream(productSeedDataDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDataDto -> {
                    Product product = modelMapper.map(productSeedDataDto, Product.class);
                    product.setSeller(this.userService.selectRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L))>0) {
                        product.setBuyer(this.userService.selectRandomUser());
                    }
                    product.setCategories(this.categoryService.getRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public Set<ProductNamePriceSellerDto> getProductsWithPriceInRangeWithNoBuyerOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
         return this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(minPrice,maxPrice)
                 .stream()
                 .map(product -> {
                     ProductNamePriceSellerDto productNamePriceSellerDto = modelMapper.map(product, ProductNamePriceSellerDto.class);
                     productNamePriceSellerDto.setSeller(String.format("%s %s",
                             product.getSeller().getFirstName(),product.getSeller().getLastName()));
                     return productNamePriceSellerDto;
                 })
                 .collect(Collectors.toSet());
    }
}
