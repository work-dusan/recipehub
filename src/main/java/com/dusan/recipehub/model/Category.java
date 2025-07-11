package com.dusan.recipehub.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String id;

    @NotBlank(message = "Category name is required")
    private String name;
}
