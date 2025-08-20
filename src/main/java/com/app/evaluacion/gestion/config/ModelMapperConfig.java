package com.app.evaluacion.gestion.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.Converter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<String, String> emptyStringToNullConverter = ctx ->
                (ctx.getSource() == null || ctx.getSource().trim().isEmpty()) ? null : ctx.getSource();

        Converter<String, Integer> stringToIntegerConverter = ctx ->
                (ctx.getSource() == null || ctx.getSource().trim().isEmpty()) ? null : Integer.valueOf(ctx.getSource());

        mapper.addConverter(emptyStringToNullConverter);
        mapper.addConverter(stringToIntegerConverter);

        return mapper;
    }
}
