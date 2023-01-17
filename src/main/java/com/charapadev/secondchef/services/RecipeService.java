package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "Recipe service")
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe create(CreateRecipeDTO createDTO) {
        Recipe recipeToCreate = Recipe.builder()
            .name(createDTO.name())
            .build();

        recipeToCreate = recipeRepository.save(recipeToCreate);
        log.info("Create recipe: {}", recipeToCreate);

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
