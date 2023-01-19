package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "Product service")
@AllArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateIngredientDTO createDTO) {
        Product productToCreate = Product.builder()
            .name(createDTO.name())
            .build();

        productToCreate = productRepository.save(productToCreate);
        log.info("Created a product: {}", productToCreate);

        return productToCreate;
    }

}
