package com.example.gymbuddy.implementation.configurations;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
//        var modelMapper = new ModelMapper();
//        modelMapper.
        return new ModelMapper();
    }

//    public Converter<Date, LocalDateTime> dateConverter() {
//        return new AbstractConverter<Date, LocalDateTime>() {
//            @Override
//            protected LocalDateTime convert(Date source) {
//                return source.toLocalDate().atTime(source.getTime());
//            }
//        }
//    }
}
