package com.springdataautomapobj.demo.configurations;

import com.springdataautomapobj.demo.models.dto.GameAddEditDto;
import com.springdataautomapobj.demo.models.entities.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper=new ModelMapper();

        modelMapper
                .typeMap(GameAddEditDto.class, Game.class)
                .addMappings(mapper->mapper.map(GameAddEditDto::getThumbnailURL,Game::setImageThumbnail));

        Converter<String, LocalDate> localDateConverter=new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH));
            }
        };

        modelMapper.addConverter(localDateConverter);
        
        modelMa

        return modelMapper;
    }
}
