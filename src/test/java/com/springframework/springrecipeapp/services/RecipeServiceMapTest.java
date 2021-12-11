package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.converters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.converters.RecipeToRecipeCommands;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceMapTest {
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommands recipeToRecipeCommands;

    RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommands);
    }

    @Test
    public void getRecipes() {
        var recipe = new Recipe();
        recipe.setId(1L);
        var _recipe = new Recipe();
        recipe.setId(2L);
        var _recipe_2 = new Recipe();
        recipe.setId(3L);
        var list = Arrays.asList(recipe, _recipe, _recipe_2);
        when(recipeRepository.findAll()).thenReturn(list);
        var listRecipe = recipeService.getRecipe();
        verify(recipeRepository).findAll();
    }

    @Test
    void getRecipe() {
        var recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        RecipeCommands recipeCommands = new RecipeCommands();
        recipeCommands.setId(1L);

        when(recipeToRecipeCommands.convert(any())).thenReturn(recipeCommands);

        var result = recipeService.saveRecipeCommand(recipeCommands);


        assertNotNull(result);
        verify(recipeCommandToRecipe).convert(any());
        verify(recipeToRecipeCommands).convert(any());



    }

    @Test
    void findById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        var byId1 = recipeService.findById(1L);
        System.out.println(byId1.getId());
        verify(recipeRepository).findById(anyLong());
    }
}