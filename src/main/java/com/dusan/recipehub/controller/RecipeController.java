package com.dusan.recipehub.controller;

import com.dusan.recipehub.dto.RecipeFormDto;
import com.dusan.recipehub.model.Category;
import com.dusan.recipehub.model.Recipe;
import com.dusan.recipehub.model.Tag;
import com.dusan.recipehub.repository.CategoryRepository;
import com.dusan.recipehub.repository.RecipeRepository;
import com.dusan.recipehub.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping("/recipes/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipeForm", new RecipeFormDto());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tagsList", tagRepository.findAll());
        return "recipe-form";
    }

    @GetMapping("/recipes/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeFormDto dto = new RecipeFormDto(
                            recipe.getName(),
                            recipe.getDescription(),
                            recipe.getPrepTimeMinutes(),
                            recipe.getServings(),
                            recipe.getCategory(),
                            recipe.getIngredients(),
                            recipe.getTags(),
                            recipe.getInstructionSteps()
                    );
                    model.addAttribute("recipeForm", dto);
                    model.addAttribute("recipeId", id);
                    model.addAttribute("categories", categoryRepository.findAll());
                    model.addAttribute("tagsList", tagRepository.findAll());
                    return "recipe-form";
                })
                .orElse("redirect:/recipes");
    }

    @PostMapping("/recipes")
    public String saveRecipe(@Valid @ModelAttribute("recipeForm") RecipeFormDto form,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("tagsList", tagRepository.findAll());
            return "recipe-form";
        }

        Category selectedCategory = categoryRepository.findById(form.getCategory().getId());

        List<Tag> selectedTags = form.getTags().stream()
                .map(tag -> tagRepository.findById(tag.getId()))
                .toList();

        Recipe newRecipe = Recipe.createWithId(
                Recipe.builder()
                        .name(form.getName())
                        .description(form.getDescription())
                        .prepTimeMinutes(form.getPrepTimeMinutes())
                        .servings(form.getServings())
                        .category(selectedCategory)
                        .ingredients(form.getIngredients())
                        .tags(selectedTags)
                        .instructionSteps(form.getInstructionSteps())
                        .build()
        );

        recipeRepository.save(newRecipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String viewRecipe(@PathVariable String id, Model model) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    model.addAttribute("recipe", recipe);
                    return "recipe-details";
                })
                .orElse("redirect:/recipes");
    }

    @PostMapping("/recipes/{id}/edit")
    public String updateRecipe(@PathVariable String id,
                               @Valid @ModelAttribute("recipeForm") RecipeFormDto form,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            model.addAttribute("recipeId", id);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("tagsList", tagRepository.findAll());
            return "recipe-form";
        }

        Category selectedCategory = categoryRepository.findById(form.getCategory().getId());

        Recipe updated = Recipe.builder()
                .id(id)
                .name(form.getName())
                .description(form.getDescription())
                .prepTimeMinutes(form.getPrepTimeMinutes())
                .servings(form.getServings())
                .category(selectedCategory)
                .ingredients(form.getIngredients())
                .tags(form.getTags())
                .instructionSteps(form.getInstructionSteps())
                .archived(false)
                .build();

        recipeRepository.update(updated);
        return "redirect:/recipes";
    }

    @PostMapping("/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeRepository.deleteById(id);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes")
    public String listRecipes(@RequestParam(name = "q", required = false) String keyword, Model model) {
        List<Recipe> result = recipeRepository.search(keyword);
        model.addAttribute("recipes", result);
        model.addAttribute("keyword", keyword);
        return "recipe-list";
    }

}
