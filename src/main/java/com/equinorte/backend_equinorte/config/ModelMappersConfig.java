package com.equinorte.backend_equinorte.config;



import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMappersConfig {
    @Bean
   public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
