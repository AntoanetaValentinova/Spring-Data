package com.jsonprocessing.demo.service;

import com.jsonprocessing.demo.model.dto.ProductNamePriceSellerDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public interface ProductService {
    void seedProducts() throws IOException;

    Set<ProductNamePriceSellerDto> getProductsWithPriceInRangeWithNoBuyerOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
