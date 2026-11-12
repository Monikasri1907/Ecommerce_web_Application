package com.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }

    @Bean
    CommandLineRunner sampleProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Product("Wireless Headphones", "Bluetooth headphones", 1499.0, 20));
                repo.save(new Product("Smart Watch", "Fitness and notification watch", 2299.0, 15));
                repo.save(new Product("Backpack", "Everyday laptop backpack", 899.0, 30));
            }
        };
    }
}
