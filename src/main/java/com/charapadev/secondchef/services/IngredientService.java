package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.dtos.ShowIngredientDTO;
import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "Ingredient service")
@AllArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    private ProductService productService;

    private Product createRelatedProduct(CreateIngredientDTO createDTO) {
        return productService.create(createDTO);
    }

    public Ingredient create(CreateIngredientDTO createDTO, Recipe recipe) {
        Product productRelated = createRelatedProduct(createDTO);

        Ingredient ingredientToCreate = Ingredient.builder()
            .quantity(createDTO.quantity())
            .recipe(recipe)
            .product(productRelated)
            .build();

        ingredientToCreate = ingredientRepository.save(ingredientToCreate);
        log.info("Created a ingredient: {}", ingredientToCreate);

        return ingredientToCreate;
    }

    public ShowIngredientDTO convertToShow(Ingredient ingredient) {
        return new ShowIngredientDTO(
            ingredient.getId(),
            ingredient.getProduct().getName(),
            ingredient.getQuantity()
        );
    }

}
