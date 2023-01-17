package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateRecipeDTO;
import com.charapadev.secondchef.dtos.ShowRecipeDTO;
import com.charapadev.secondchef.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<ShowRecipeDTO>> list() {
        List<ShowRecipeDTO> recipes = recipeService.findAll();

        return ResponseEntity.ok(recipes);
    }

    @PostMapping
    public ResponseEntity<ShowRecipeDTO> create(@Valid @RequestBody CreateRecipeDTO createDTO) {
        ShowRecipeDTO recipeCreated = recipeService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipeCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowRecipeDTO> findOne(@PathVariable("id") UUID recipeId) {
        ShowRecipeDTO recipeFound = recipeService.findOneToShow(recipeId);

        return ResponseEntity.ok(recipeFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID recipeId) {
        recipeService.delete(recipeId);

        return ResponseEntity.noContent().build();
    }

}
