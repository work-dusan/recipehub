package com.dusan.recipehub.dto;

import com.dusan.recipehub.model.Category;
import com.dusan.recipehub.model.Ingredient;
import com.dusan.recipehub.model.Tag;
import com.dusan.recipehub.model.InstructionStep;
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

    @NotNull(message = "Category is required")
    private Category category;

    @Size(min = 1, message = "At least one ingredient is required")
    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Tag> tags = new ArrayList<>();

    private List<InstructionStep> instructionSteps = new ArrayList<>();
}
