package com.dusan.recipehub.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @Min(value = 1, message = "Preparation time must be at least 1 minute")
    private int prepTimeMinutes;

    @Min(value = 1)
    private int servings;

    @NotBlank
    private Category category; // npr. "Desert", "Glavno jelo"

    @NotNull
    @Size(min = 1, message = "Recipe must have at least one ingredient")
    private List<Ingredient> ingredients;

    private List<Tag> tags; // Dodatak: oznake poput "bez glutena", "brzo", itd.

    private List<InstructionStep> instructionSteps; // Dodatak: koraci pripreme

    private boolean archived;

    public static Recipe createWithId(Recipe recipeWithoutId) {
        return Recipe.builder()
                .id(UUID.randomUUID().toString())
                .name(recipeWithoutId.getName())
                .description(recipeWithoutId.getDescription())
                .prepTimeMinutes(recipeWithoutId.getPrepTimeMinutes())
                .servings(recipeWithoutId.getServings())
                .category(recipeWithoutId.getCategory())
                .ingredients(recipeWithoutId.getIngredients())
                .tags(recipeWithoutId.getTags())
                .instructionSteps(recipeWithoutId.getInstructionSteps())
                .archived(false)
                .build();
    }
}
