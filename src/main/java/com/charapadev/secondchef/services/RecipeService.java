package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.dtos.ShowIngredientDTO;
import com.charapadev.secondchef.dtos.ShowRecipeDTO;
import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service used to manipulate the {@link Recipe recipe} instances and trigger other service methods like
 * {@link IngredientService ingredient service}.
 */

@Service
@Slf4j(topic = "Recipe service")
@Transactional
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ItemService itemService;

    /**
     * Triggers the creation of some {@link Ingredient ingredients} related to a {@link Recipe recipe}.
     * <p>
     * This method calls the {@link IngredientService#create(CreateIngredientDTO, Recipe) ingredient creation function}
     * and link the returned objects to a specific recipe.
     *
     * @param ingredientDTOS The list with data for each ingredient.
     * @param recipe The recipe to link the ingredients.
     * @see IngredientService Ingredient service.
     */
    private void createRelatedIngredients(List<CreateIngredientDTO> ingredientDTOS, Recipe recipe) {
        Set<Ingredient> ingredientsRelated = ingredientDTOS.stream()
            .map(ingredientDTO -> ingredientService.create(ingredientDTO, recipe))
            .collect(Collectors.toSet());

        recipe.setIngredients(ingredientsRelated);
        recipeRepository.save(recipe);
    }

    /**
     * Searches all the {@link Recipe recipes} available.
     *
     * @return The list of recipes found.
     */
    public List<ShowRecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
            .map(this::convertToShow)
            .toList();
    }

    /**
     * Checks if a {@link Recipe recipe} is unique.
     * <p>
     * To be considered unique, a recipe MUST have exclusives ID and NAME.
     *
     * @param recipe The recipe to check.
     * @throws RuntimeException If the recipe already exists on application.
     */
    private void isUnique(Recipe recipe) throws RuntimeException {
        boolean recipeAlreadyExists = recipeRepository.existsByName(recipe.getName());

        if (recipeAlreadyExists) {
            throw new RuntimeException("This recipe already exists");
        }
    }

    /**
     * Creates an instance of {@link Recipe recipe} and the related {@link Ingredient ingredients}.
     * <p>
     * The method don't create the related ingredients by itself, but intermediate the creation using the
     * {@link #createRelatedIngredients(List, Recipe) related ingredients creation method}.
     *
     * @param createDTO The creation information.
     * @return The created recipe.
     * @see CreateRecipeDTO Creation recipe schema.
     */
    public ShowRecipeDTO create(CreateRecipeDTO createDTO) {
        Recipe recipeToCreate = Recipe.builder()
            .name(createDTO.name())
            .build();

        isUnique(recipeToCreate);

        recipeToCreate = recipeRepository.save(recipeToCreate);
        createRelatedIngredients(createDTO.ingredients(), recipeToCreate);

        log.info("Created a recipe: {}", recipeToCreate);
        return convertToShow(recipeToCreate);
    }

    /**
     * Searches and returns an instance of {@link Recipe recipe} using the given ID.
     *
     * @param recipeId The recipe ID.
     * @return The recipe found.
     * @throws NoSuchElementException If none recipe is found.
     */
    public Recipe findOne(UUID recipeId) throws NoSuchElementException {
        return recipeRepository.findById(recipeId)
            .orElseThrow();
    }

    /**
     * Searches and returns a shorted instance of {@link Recipe recipe} using the given ID.
     * <p>
     * This method has the same purpose of {@link #findOne(UUID) findOne method}, but clear the data of recipe before return.
     *
     * @param recipeId The recipe ID.
     * @return The shorted recipe found.
     */
    public ShowRecipeDTO findOneToShow(UUID recipeId) {
        Recipe recipeFound = findOne(recipeId);

        return convertToShow(recipeFound);
    }

    /**
     * Converts a given {@link Recipe recipe} to an {@link ShowRecipeDTO exposable DTO}.
     * <p>
     * This method don't transform the data of recipe, but trigger the transformation of others entities related
     * like the ingredients.
     * 
     * @param recipe The recipe to convert.
     * @return The recipe converted.
     * @see IngredientService#convertToShow(Ingredient) Ingredient convertion method.
     */
    private ShowRecipeDTO convertToShow(Recipe recipe) {
        List<ShowIngredientDTO> ingredientsToShow = recipe.getIngredients().stream()
            .map(ingredient -> ingredientService.convertToShow(ingredient))
            .toList();

        return new ShowRecipeDTO(
            recipe.getId(),
            recipe.getName(),
            ingredientsToShow
        );
    }

    /**
     * Removes an instance of {@link Recipe recipe} from application using the given ID.
     *
     * @param recipeId The recipe ID.
     */
    public void delete(UUID recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    /**
     * Lists all the {@link Recipe recipes} available to a user cook.
     * <p>
     * First, the repository catches the recipes that has at least ONE ingredient in common with user {@link Item items}.
     * After this, other function is called to check the ingredient and items quantities.
     *
     * @param userEmail The user email address.
     * @return The list of recipes available.
     * @see #filterAvailableRecipes(Stream, String)  Filter recipes function.
     */
    public List<ShowRecipeDTO> listAvailable(String userEmail) {
        Stream<Recipe> recipesPossible = recipeRepository.findRecipesPossibleForUser(userEmail);

        return filterAvailableRecipes(recipesPossible, userEmail)
            .map(this::convertToShow)
            .toList();
    }

    /**
     * Checks and filters the {@link Recipe recipes} possible to cook.
     * <p>
     * This method checks if a recipe can be cooked using the quantity of each {@link Ingredient ingredient}.
     * If the user given has a specific {@link Item item} related to same product as the ingredient and the quantity
     * is enough, this recipe will not be filtered.
     * <p>
     * The specific function that checks the quantity and searches the related product can be found in
     * {@link ItemService item service}.
     *
     * @param recipesPossibles The list of possible recipes.
     * @param userEmail  The user email address.
     * @return The list of recipes available.
     * @see ItemService#hasQuantityEnough(long, UUID, String) Item quantity check function. 
     */
    private Stream<Recipe> filterAvailableRecipes(Stream<Recipe> recipesPossibles, String userEmail) {
        return recipesPossibles.filter(recipe -> {
            Set<Ingredient> ingredients = recipe.getIngredients();

            return ingredients.stream()
                .allMatch(ingredient -> {
                    long ingredientQuantity = ingredient.getQuantity();
                    UUID productRelatedID = ingredient.getProduct().getId();

                    return itemService.hasQuantityEnough(ingredientQuantity, productRelatedID, userEmail);
                });
        });
    }

}
