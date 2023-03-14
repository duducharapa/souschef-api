package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateIngredientDTO;
import com.charapadev.secondchef.dtos.CreateItemDTO;
import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Service used to create the default data for new clients test the functionalities of application.
 * <p>
 * The default data is:
 * <p>- A test {@link User user}.
 * <p>- A grilled cheese {@link Recipe recipe}.
 * <p>- The {@link Item items} enough to the user test cook a grilled cheese.
 */

@Service
@Transactional
@Slf4j(topic = "Default data service")
public class DefaultDataService {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductService productService;

    public void loadDefaultData() {
        User defaultUser = createDefaultUser();
        createDefaultRecipes();
        createDefaultItems(defaultUser);
    }

    /**
     * Creates the default {@link User user} on application.
     * <p>
     * This user is used to new clients interact with the system, containing default {@link Item items}
     * to use on a default {@link Recipe recipe}.
     * 
     * @return The user created.
     * @see #createDefaultRecipes() Default recipe creation
     * @see #createDefaultItems(User) Default items creation.
     */
    private User createDefaultUser() {
        CreateUserDTO defaultUserDTO = new CreateUserDTO(
            "teste@teste.com",
            "teste123"
        );

        return userService.findOrCreate(defaultUserDTO);
    }

    /**
     * Creates the default {@link Recipe recipe} on application.
     * <p>
     * First, the function create the {@link Ingredient ingredients} used to cook a <b>grilled cheese</b>.
     * The ingredients are: 1 quantity of <b>cheese</b>, 1 quantity of <b>ham</b> and 2 quantities of <b>bread</b>.
     * <p>
     * Created it, the recipe is saved and available to be cooked.
     *
     * @see #createDefaultItems(User)  Default items creation.
     */
    private void createDefaultRecipes() {
        CreateIngredientDTO cheese = new CreateIngredientDTO("Queijo", 1L);
        CreateIngredientDTO ham = new CreateIngredientDTO("Presunto", 1L);
        CreateIngredientDTO bread = new CreateIngredientDTO("Pão", 2L);

        CreateRecipeDTO grilledCheeseDTO = new CreateRecipeDTO(
            "Misto quente",
            List.of(bread, cheese, ham)
        );

        recipeService.create(grilledCheeseDTO);
    }

    /**
     * Inserts the default {@link Item items} to a specific {@link User user}.
     * <p>
     * The items created here share the same {@link Product product} of {@link Ingredient ingredients}
     * created on {@link #createDefaultRecipes() default recipe creation}.
     *
     * @param owner The owner of default items.
     * @see #createDefaultRecipes()  Create default recipes.
     */
    private void createDefaultItems(User owner) {
        UUID cheeseId = productService.findByName("Queijo").orElseThrow().getId();
        UUID hamId = productService.findByName("Presunto").orElseThrow().getId();
        UUID breadId = productService.findByName("Pão").orElseThrow().getId();

        CreateItemDTO cheese = new CreateItemDTO(cheeseId, 2);
        CreateItemDTO ham = new CreateItemDTO(hamId, 1);
        CreateItemDTO bread = new CreateItemDTO(breadId, 4);

        Stream.of(bread, cheese, ham).forEach(item -> itemService.create(item, owner));
    }

}
