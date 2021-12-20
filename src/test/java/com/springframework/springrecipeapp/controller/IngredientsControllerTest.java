package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.services.IngredientServices;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientsControllerTest {
    @Mock
    IngredientServices services;
    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService oumService;

    IngredientsController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new IngredientsController(services, recipeService, oumService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredients() throws Exception {

        //given
        var command = new RecipeCommand();
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

    @Test
    void showRecipeIngredient() throws Exception {
        //given
        var ingredientCommand = new IngredientsCommand();
        ingredientCommand.setId(1L);
        //when
        when(services.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(status().isOk());
        //then
        verify(services).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    void testNewIngredientForm() throws Exception {
        //given
        var recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(oumService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void newIngredientTest() throws Exception {
        //given
        var ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(3L);
        ingredientsCommand.setRecipeId(1L);
        //when
        when(services.saveIngredientCommand(any())).thenReturn(ingredientsCommand);
        //then
        mockMvc.perform(post("/recipe/1/ingredient")
                        .param("id","")
                        .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/3/show"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        //given
        //then
        mockMvc.perform(get("recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

    }
}