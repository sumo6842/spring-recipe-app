package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.services.IngredientServices;
import com.springframework.springrecipeapp.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IngredientsController {
    private final IngredientServices ingredientService;
    private final RecipeService recipeService;
//    private final UnitOfMeasureService uomService;

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredients list for recipe id: " + recipeId);
        var command = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", command);
        return "recipe/ingredient/list";
    }

}
