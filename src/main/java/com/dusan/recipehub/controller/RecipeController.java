package com.dusan.recipehub.controller;

import com.dusan.recipehub.dto.RecipeFormDto;
import com.dusan.recipehub.model.Recipe;
import com.dusan.recipehub.repository.RecipeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/recipes/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipeForm", new RecipeFormDto());
        return "recipe-form";
    }

    @PostMapping("/recipes")
    public String saveRecipe(@Valid @ModelAttribute("recipeForm") RecipeFormDto form,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "recipe-form";
        }

        Recipe newRecipe = Recipe.createWithId(
                Recipe.builder()
                        .name(form.getName())
                        .description(form.getDescription())
                        .prepTimeMinutes(form.getPrepTimeMinutes())
                        .servings(form.getServings())
                        .category(form.getCategory())
                        .ingredients(form.getIngredients())
                        .build()
        );

        recipeRepository.save(newRecipe);
        return "redirect:/recipes";
    }
}
