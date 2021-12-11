package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        var byId = recipeService.findById(id);
        model.addAttribute("recipe", byId);
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommands());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommands commands) {
        RecipeCommands saved = recipeService.saveRecipeCommand(commands);
        return "redirect:/recipe/show/" + saved.getId();
    }


}
