package com.jsonprocessing.demo.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsonprocessing.demo.model.dto.CategoryCountProductsDto;
import com.jsonprocessing.demo.model.dto.ProductNamePriceSellerDto;
import com.jsonprocessing.demo.model.dto.ProductWithBuyerDto;
import com.jsonprocessing.demo.model.dto.UserWithSoldProductsDto;
import com.jsonprocessing.demo.model.entity.Category;
import com.jsonprocessing.demo.model.entity.Product;
import com.jsonprocessing.demo.model.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper=new ModelMapper();

//        Converter<User,String> sellerStringConverter= new Converter<User, String>() {
//            @Override
//            public String convert(MappingContext<User, String> mappingContext) {
//                 String userName=String.format("%s %s",mappingContext.getSource().getFirstName(),mappingContext.getSource().getLastName());
//                 return userName;
//            }
//        };

//        modelMapper.addConverter(sellerStringConverter);

//        modelMapper.typeMap(ProductNamePriceSellerDto.class, Product.class);

        modelMapper
                .typeMap(Category.class, CategoryCountProductsDto.class)
                .addMappings(mapper->mapper.map(Category::getName,CategoryCountProductsDto::setCategory));


        return  modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
