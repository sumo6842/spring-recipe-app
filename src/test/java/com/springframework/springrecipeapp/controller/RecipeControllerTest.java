package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @Test
    void showById() throws Exception {
        var recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void newRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void updateRecipe() throws Exception {
        var recipeCommands = new RecipeCommands();
        recipeCommands.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommands);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        var recipeCommands = new RecipeCommands();
        recipeCommands.setId(1L);
        when(recipeService.saveRecipeCommand(any()))
                .thenReturn(recipeCommands);
        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "some string")
                        .param("id",""))
                .andExpect(redirectedUrl("/recipe/show/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/" + 1));
    }

    @Test
    void deletedById() throws Exception {

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService).deleteByIdLong(anyLong());
    }
}