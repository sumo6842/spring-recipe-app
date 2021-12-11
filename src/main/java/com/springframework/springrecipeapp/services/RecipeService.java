package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.model.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipe();
    Recipe findById(Long id);
}
