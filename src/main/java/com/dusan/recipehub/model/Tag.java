package com.dusan.recipehub.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String id;

    @NotBlank(message = "Tag name is required")
    private String name;
}