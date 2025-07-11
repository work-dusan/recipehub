package com.dusan.recipehub.repository;

import com.dusan.recipehub.model.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryRepository {

    private final List<Category> categories = Arrays.asList(
            new Category("1", "Glavno jelo"),
            new Category("2", "Predjelo"),
            new Category("3", "Desert"),
            new Category("4", "Salata"),
            new Category("5", "Supe i čorbe")
    );

    public List<Category> findAll() {
        return categories;
    }

    public Category findById(String id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
