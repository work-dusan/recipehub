package com.dusan.recipehub.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @NotBlank(message = "Ingredient name is required")
    private String name;

    @DecimalMin(value = "0.01", message = "Quantity must be positive")
    private double quantity;

    @NotBlank(message = "Unit is required")
    private String unit; // npr. "g", "ml", "kašičica", "kom"
}
