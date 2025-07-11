package com.dusan.recipehub.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructionStep {
    @Min(1)
    private int stepNumber;

    @NotBlank(message = "Step description is required")
    private String description;
}
