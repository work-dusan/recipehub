package com.dusan.recipehub.controller;

import com.dusan.recipehub.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping({"/", "/recipes"})
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipe-list"; // Thymeleaf view
    }
}
