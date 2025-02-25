package com.estebanst99.recipe_api.controller;

import com.estebanst99.recipe_api.dto.Recipe;
import com.estebanst99.recipe_api.exception.ResourceNotFoundException;
import com.estebanst99.recipe_api.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(201).body(newRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
        return ResponseEntity.ok("Receta actualizada con ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Receta eliminada con ID: " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String name) {
        List<Recipe> recipes = recipeService.searchRecipesByName(name);
        if (recipes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron recetas con el nombre: " + name);
        }
        return ResponseEntity.ok(recipes);
    }
}
