package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to manipulate the {@link Product product} instances.
 */

@Service
@Slf4j(topic = "Product service")
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates an instance of {@link Product product}.
     *
     * @param createDTO The creation information.
     * @return The created product.
     * @see CreateIngredientDTO Creation product schema.
     */
    public Product create(CreateIngredientDTO createDTO) {
        Product productToCreate = Product.builder()
            .name(createDTO.name())
            .build();

        productToCreate = productRepository.save(productToCreate);
        log.info("Created a product: {}", productToCreate);

        return productToCreate;
    }

}
