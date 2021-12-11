package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Captor
    ArgumentCaptor<Set<Recipe>> argumentCaptor;

    IndexController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        var index = controller.getIndexPage(model);
        assertEquals(index, "index");
    }

//    @Test
//    public void testMockMVC() {
//        MockMvc mockMvc = MockMvcBuilder
//    }

    @Test
    public void setRecipeTest() {
        var recipes = new HashSet<Recipe>();
        recipes.add(new Recipe());

        var re_2 = new Recipe();
        re_2.setId(2L);
        recipes.add(re_2);

        assertEquals(recipes.size(), 2);

        when(recipeService.getRecipe()).thenReturn(recipes);

        var indexPage = controller.getIndexPage(model);
        assertEquals("index", indexPage);
        verify(recipeService, times(1)).getRecipe();

        verify(model, times(1))
                .addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}