package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.IngredientsCommand;

public interface IngredientServices {
    IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientsCommand saveIngredientCommand(IngredientsCommand command);

    void deleteIngredient(Long recipeId, Long id);
}
