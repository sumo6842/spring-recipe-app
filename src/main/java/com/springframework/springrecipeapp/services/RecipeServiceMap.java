package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceMap implements RecipeService{
    private final RecipeRepository recipeRepository;

    @Override
    public Set<Recipe> getRecipe() {
        var recipes = new HashSet<Recipe>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        var byId = recipeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RuntimeException("Null recipe returned");
        }
        return byId.get();
    }
}
