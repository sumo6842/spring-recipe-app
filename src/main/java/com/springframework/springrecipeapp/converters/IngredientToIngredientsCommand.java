package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientToIngredientsCommand implements Converter<Ingredient, IngredientsCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    @Override
    public IngredientsCommand convert(Ingredient source) {

        final var ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(source.getId());
        ingredientsCommand.setAmount(source.getAmount());
        ingredientsCommand.setDescription(source.getDescription());
        ingredientsCommand.setRecipeId(source.getRecipe().getId());
        ingredientsCommand.setUom(uomConverter.convert(source.getUom()));
        return ingredientsCommand;
    }
}
