package com.lerniludi.lingvoj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

/**
 * Configuration de l'application
 */
@Configuration
public class AppConfig {

    /**
     * Register pour Spring de ModelMapper en tant que Bean
     *
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
