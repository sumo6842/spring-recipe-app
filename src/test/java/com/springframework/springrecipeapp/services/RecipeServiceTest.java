package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.converters.RecipeCommandToRecipe;
import com.springframework.springrecipeapp.converters.RecipeToRecipeCommands;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
//@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceTest {

    public final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeCommandToRecipe recipeConverter;

    @Autowired
    RecipeToRecipeCommands recipeCommandsConverter;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipe() {
    }

    @Test
    void findById() {
    }

    @Test
    @Transactional
    void saveRecipeCommand() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();

        Recipe testRecipe = recipes.iterator().next();

        var recipeCommand = recipeCommandsConverter.convert(testRecipe);

        //when
        recipeCommand.setDescription(NEW_DESCRIPTION);
        var savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        //then
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());

    }
}