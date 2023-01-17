package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "Recipe service")
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    private void createRelatedIngredients(List<CreateIngredientDTO> ingredientDTOS, Recipe recipe) {
        Set<Ingredient> ingredientsRelated = ingredientDTOS.stream()
            .map(ingredientDTO -> ingredientService.create(ingredientDTO, recipe))
            .collect(Collectors.toSet());

        recipe.setIngredients(ingredientsRelated);
        recipeRepository.save(recipe);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe create(CreateRecipeDTO createDTO) {
        Recipe recipeToCreate = Recipe.builder()
            .name(createDTO.name())
            .build();

        recipeToCreate = recipeRepository.save(recipeToCreate);
        createRelatedIngredients(createDTO.ingredients(), recipeToCreate);

        log.info("Created a recipe: {}", recipeToCreate);
        return recipeToCreate;
    }

    public Recipe findOne(UUID recipeId) {
        return recipeRepository.findById(recipeId)
            .orElseThrow();
    }

    public void delete(UUID recipeId) {
        recipeRepository.deleteById(recipeId);
    }

}
