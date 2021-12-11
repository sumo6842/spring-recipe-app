package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceMapTest {
    @Mock
    RecipeRepository recipeRepository;
//    @InjectMocks
    RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        recipeService = new RecipeServiceMap(recipeRepository);
    }

    @Test
    public void getRecipes() {

    }

    @Test
    void getRecipe() {
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