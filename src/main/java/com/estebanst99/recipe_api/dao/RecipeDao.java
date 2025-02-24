package com.estebanst99.recipe_api.dao;

import com.estebanst99.recipe_api.dto.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeDao {

    List<Recipe> getAllRecipes();
    Optional<Recipe> getRecipeById(Long id);
    void createRecipe(Recipe recipe);
    void updateRecipe(Long id, Recipe recipe);
    void deleteRecipe(Long id);
    List<Recipe> searchRecipesByName(String name);
}
