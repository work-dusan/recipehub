package com.dusan.recipehub.dto;

import com.dusan.recipehub.model.Ingredient;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFormDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @Min(1)
    private int prepTimeMinutes;

    @Min(1)
    private int servings;

    @NotBlank
    private String category;

    @Size(min = 1, message = "At least one ingredient is required")
    private List<Ingredient> ingredients = new ArrayList<>();
}
