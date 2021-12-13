package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.services.IngredientServices;
import com.springframework.springrecipeapp.services.RecipeService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientsControllerTest {
    @Mock
    IngredientServices services;
    @Mock
    RecipeService recipeService;

    IngredientsController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new IngredientsController(services, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredients() throws Exception {

        //given
        var command = new RecipeCommands();
        command.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        //then
        verify(recipeService).findCommandById(anyLong());

    }
}