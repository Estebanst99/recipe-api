package com.estebanst99.recipe_api.service;

import com.estebanst99.recipe_api.dao.RecipeDao;
import com.estebanst99.recipe_api.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeDao recipeDao;

    @Autowired
    public RecipeService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeDao.getRecipeById(id);
    }

    public Recipe createRecipe(Recipe recipe) {
        recipeDao.createRecipe(recipe);
        return recipe;
    }

    public void updateRecipe(Long id, Recipe recipe) {
        recipeDao.updateRecipe(id, recipe);
    }

    public void deleteRecipe(Long id) {
        recipeDao.deleteRecipe(id);
    }

    public List<Recipe> searchRecipesByName(String name) {
        return recipeDao.searchRecipesByName(name);
    }
}
