package com.estebanst99.recipe_api.dao.impl;

import com.estebanst99.recipe_api.dao.RecipeDao;
import com.estebanst99.recipe_api.dto.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RecipeDaoImpl implements RecipeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDaoImpl.class);
    private final DataSource recipeDataSource;

    @Autowired
    public RecipeDaoImpl(@Qualifier("postgresDS")DataSource recipeDataSource) {
        this.recipeDataSource = recipeDataSource;
    }
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes";

        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getLong("id"));
                recipe.setTitle(rs.getString("title"));
                recipe.setDescription(rs.getString("description"));
                // Aquí se asume que "ingredients" es un JSON almacenado como String
                recipe.setIngredients(List.of(rs.getString("ingredients").split(",")));

                recipes.add(recipe);
            }
        } catch (Exception e) {
            LOGGER.error("Error getting all recipes", e);
        }
        return recipes;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        return null;
    }

    @Override
    public void createRecipe(Recipe recipe) {

    }

    @Override
    public void updateRecipe(Long id, Recipe recipe) {

    }

    @Override
    public void deleteRecipe(Long id) {

    }

    @Override
    public List<Recipe> searchRecipesByName(String name) {
        return null;
    }
}
