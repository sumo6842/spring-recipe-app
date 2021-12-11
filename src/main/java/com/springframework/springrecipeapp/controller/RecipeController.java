package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable Long id, Model model) {
        var byId = recipeService.findById(id);
        model.addAttribute("recipe", byId);
        return "recipe/show";
    }

}
