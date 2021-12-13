package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.converters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.converters.RecipeToRecipeCommands;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommands recipeToRecipeCommands;
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

    @Override
    @Transactional
    public RecipeCommands findCommandById(Long id) {
        return recipeToRecipeCommands.convert(findById(id));
    }


    @Override
    @Transactional
    public RecipeCommands saveRecipeCommand(RecipeCommands recipeCommand) {
        var detachRecipe = recipeCommandToRecipe.convert(recipeCommand);
        if (detachRecipe == null) return null;
        var savedRecipe = recipeRepository.save(detachRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());

        return recipeToRecipeCommands.convert(savedRecipe);
    }

    @Override
    public void deleteByIdLong(Long id) {
        recipeRepository.deleteById(id);
    }


}
