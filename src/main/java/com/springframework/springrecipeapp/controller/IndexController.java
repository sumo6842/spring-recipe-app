package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.model.Category;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.model.UnitOfMeasure;
import com.springframework.springrecipeapp.repository.CategoryRepository;
import com.springframework.springrecipeapp.repository.UnitOfMeasureRepository;
import com.springframework.springrecipeapp.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final RecipeService service;

    @RequestMapping({"/", "/index", "/index/page"})
    public String getIndexPage(Model model) {
        var recipe = service.getRecipe();
        model.addAttribute("recipes", recipe);
        return "index";
    }
}
