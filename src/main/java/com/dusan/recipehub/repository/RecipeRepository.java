package com.dusan.recipehub.repository;

import com.dusan.recipehub.model.Ingredient;
import com.dusan.recipehub.model.Recipe;
import com.dusan.recipehub.model.Category;
import com.dusan.recipehub.model.Tag;
import com.dusan.recipehub.model.InstructionStep;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecipeRepository {
    private final Map<String, Recipe> recipeMap = new ConcurrentHashMap<>();

    public RecipeRepository() {
        var testRecipe = Recipe.createWithId(Recipe.builder()
                .name("Pasta Carbonara")
                .description("Simple and creamy pasta dish")
                .prepTimeMinutes(20)
                .servings(2)
                .category(new Category("1", "Glavno jelo"))
                .ingredients(List.of(
                        Ingredient.builder().name("Pasta").quantity(200).unit("g").build(),
                        Ingredient.builder().name("Jaja").quantity(2).unit("kom").build(),
                        Ingredient.builder().name("Slanina").quantity(100).unit("g").build()
                ))
                .tags(List.of(
                        new Tag("1", "Brzo"),
                        new Tag("3", "Popularno")
                ))
                .instructionSteps(List.of(
                        new InstructionStep(1, "Skuvaj pastu prema uputstvu sa pakovanja."),
                        new InstructionStep(2, "U tiganju proprži slaninu."),
                        new InstructionStep(3, "Pomešaj jaja i sir, pa sjedini sa pastom i slaninom.")
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

    public void update(Recipe updatedRecipe) {
        recipeMap.put(updatedRecipe.getId(), updatedRecipe);
    }

    public void deleteById(String id) {
        recipeMap.remove(id);
    }

    public List<Recipe> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        String lowerKeyword = keyword.toLowerCase();

        return recipeMap.values().stream()
                .filter(r -> !r.isArchived() && (
                        r.getName().toLowerCase().contains(lowerKeyword)
                                || (r.getCategory() != null && r.getCategory().getName().toLowerCase().contains(lowerKeyword))
                                || (r.getTags() != null && r.getTags().stream()
                                .anyMatch(tag -> tag.getName().toLowerCase().contains(lowerKeyword)))
                                || (r.getInstructionSteps() != null && r.getInstructionSteps().stream()
                                .anyMatch(step -> step.getDescription().toLowerCase().contains(lowerKeyword)))
                ))
                .toList();
    }
}
