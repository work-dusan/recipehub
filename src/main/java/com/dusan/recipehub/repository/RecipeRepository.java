package com.dusan.recipehub.repository;

import com.dusan.recipehub.model.Ingredient;
import com.dusan.recipehub.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecipeRepository {
    private final Map<String, Recipe> recipeMap = new ConcurrentHashMap<>();

    public RecipeRepository() {
        // Dodaj test recept
        var testRecipe = Recipe.createWithId(Recipe.builder()
                .name("Pasta Carbonara")
                .description("Simple and creamy pasta dish")
                .prepTimeMinutes(20)
                .servings(2)
                .category("Glavno jelo")
                .ingredients(List.of(
                        Ingredient.builder().name("Pasta").quantity(200).unit("g").build(),
                        Ingredient.builder().name("Jaja").quantity(2).unit("kom").build(),
                        Ingredient.builder().name("Slanina").quantity(100).unit("g").build()
                ))
                .build());

        save(testRecipe);
    }

    public List<Recipe> findAll() {
        return recipeMap.values().stream()
                .filter(recipe -> !recipe.isArchived())
                .toList();
    }

    public Optional<Recipe> findById(String id) {
        return Optional.ofNullable(recipeMap.get(id));
    }

    public void save(Recipe recipe) {
        recipeMap.put(recipe.getId(), recipe);
    }

    public void delete(String id) {
        Recipe r = recipeMap.get(id);
        if (r != null) {
            r.setArchived(true);
        }
    }

    public void update(Recipe updatedRecipe) {
        recipeMap.put(updatedRecipe.getId(), updatedRecipe);
    }
}
