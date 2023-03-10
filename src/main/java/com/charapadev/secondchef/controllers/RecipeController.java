package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.dtos.ShowRecipeDTO;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Controller used to manipulate anything about {@link Recipe recipe} instances.
 *<p>
 * To access any resource here, the user must be fully authenticated.
 */

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    /**
     * Lists all {@link Recipe recipes} registered on application.
     *
     * @return The 200(OK) HTTP code with the list of recipes found.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping
    public ResponseEntity<List<ShowRecipeDTO>> list() {
        List<ShowRecipeDTO> recipes = recipeService.findAll();

        return ResponseEntity.ok(recipes);
    }

    /**
     * Inserts a new {@link Recipe recipe} on application.
     *
     * @param createDTO The initial data the recipe should have.
     * @return The 201(Created) HTTP code with the created recipe.
     *
     * @see CreateRecipeDTO Create recipe schema.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/201">HTTP 201 code specification.</a>
     */
    @PostMapping
    public ResponseEntity<ShowRecipeDTO> create(@Valid @RequestBody CreateRecipeDTO createDTO) {
        ShowRecipeDTO recipeCreated = recipeService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipeCreated);
    }

    /**
     * Searches a unique {@link Recipe recipe} using the given identifier.
     *
     * @param recipeId The recipe ID.
     * @return The 200(OK) HTTP code with the recipe found.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShowRecipeDTO> findOne(@PathVariable("id") UUID recipeId) {
        ShowRecipeDTO recipeFound = recipeService.findOneToShow(recipeId);

        return ResponseEntity.ok(recipeFound);
    }

    /**
     * Removes an instance of {@link Recipe recipe} using the given identifier.
     *
     * @param recipeId The recipe ID.
     * @return The 204(No Content) HTTP code.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/204">HTTP 204 code specification.</a>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID recipeId) {
        recipeService.delete(recipeId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Lists the {@link Recipe recipes} available to cook by a specific user.
     *
     * @return The 200(OK) HTTP code with the list of recipes available.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping("/available")
    public ResponseEntity<List<ShowRecipeDTO>> listAvailable(Authentication auth) {
        List<ShowRecipeDTO> recipesAvailable = recipeService.listAvailable(auth.getName());

        return ResponseEntity.ok(recipesAvailable);
    }

}
