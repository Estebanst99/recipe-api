package com.estebanst99.recipe_api.dao.impl;

import com.estebanst99.recipe_api.dao.RecipeDao;
import com.estebanst99.recipe_api.dto.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RecipeDaoImpl implements RecipeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDaoImpl.class);
    private final DataSource recipeDataSource;
    private static final String SELECT_ALL_RECIPES = "SELECT * FROM recipes";
    private static final String SELECT_RECIPE_BY_ID = "SELECT * FROM recipes WHERE id = ?";
    private static final String INSERT_RECIPE = "INSERT INTO recipes (title, description, ingredients) VALUES (?, ?, ?)";
    private static final String UPDATE_RECIPE = "UPDATE recipes SET title = ?, description = ?, ingredients = ? WHERE id = ?";
    private static final String DELETE_RECIPE = "DELETE FROM recipes WHERE id = ?";
    private static final String SEARCH_RECIPES_BY_NAME = "SELECT * FROM recipes WHERE title ILIKE ?";

    @Autowired
    public RecipeDaoImpl(@Qualifier("postgresDS")DataSource recipeDataSource) {
        this.recipeDataSource = recipeDataSource;
    }
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_RECIPES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                recipes.add(mapRowToRecipe(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all recipes", e);
        }
        return recipes;
    }

    private Recipe mapRowToRecipe(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getLong("id"));
        recipe.setTitle(rs.getString("title"));
        recipe.setDescription(rs.getString("description"));
        recipe.setIngredients(List.of(rs.getString("ingredients").split(",")));
        return recipe;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_RECIPE_BY_ID)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToRecipe(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting recipe by ID: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public void createRecipe(Recipe recipe) {
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_RECIPE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getDescription());
            ps.setString(3, String.join(",", recipe.getIngredients()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        recipe.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating recipe", e);
        }
    }

    @Override
    public void updateRecipe(Long id, Recipe recipe) {
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_RECIPE)) {

            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getDescription());
            ps.setString(3, String.join(",", recipe.getIngredients()));
            ps.setLong(4, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating recipe with ID: {}", id, e);
        }
    }

    @Override
    public void deleteRecipe(Long id) {
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_RECIPE)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting recipe with ID: {}", id, e);
        }
    }

    @Override
    public List<Recipe> searchRecipesByName(String name) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = recipeDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_RECIPES_BY_NAME)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recipes.add(mapRowToRecipe(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error searching recipes by name: {}", name, e);
        }
        return recipes;
    }
}
