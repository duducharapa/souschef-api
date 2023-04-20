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

    /**
     * Creates, if necessary, the {@link Product} related to this ingredient.
     * <p>
     * This method uses the same DTO as input used on ingredient creation because these entities has the same information
     * to be provided on creation.
     *
     * @param createDTO The creation information.
     * @return The created product.
     * @see Product Product schema.
     * @see CreateIngredientDTO Create product/ingredient schema.
     */
    private Product createRelatedProduct(CreateIngredientDTO createDTO) {
        return productService.findOrCreate(createDTO);
    }

    /**
     * Create an {@link Ingredient ingredient} and the {@link Product product} related.
     *
     * @param createDTO The creation information.
     * @param recipe The recipe to link on ingredient.
     * @return The created ingredient.
     *
     * @see CreateIngredientDTO Create ingredient schema.
     */
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

    /**
     * Converts a given {@link Ingredient ingredient} to an {@link ShowIngredientDTO exposable DTO}.
     * <p>
     *  This conversion removes the overload of attributes related to link between entities that are also stored.
     *
     * @param ingredient The ingredient to convert.
     * @return The converted ingredient.
     */
    public ShowIngredientDTO convertToShow(Ingredient ingredient) {
        return new ShowIngredientDTO(
            ingredient.getId(),
            ingredient.getProduct().getName(),
            ingredient.getQuantity()
        );
    }

}
