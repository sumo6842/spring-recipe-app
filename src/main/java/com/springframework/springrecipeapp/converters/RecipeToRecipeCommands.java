package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.RecipeCommands;
import com.springframework.springrecipeapp.model.Category;
import com.springframework.springrecipeapp.model.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RecipeToRecipeCommands implements Converter<Recipe, RecipeCommands> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientsCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    @Synchronized
    @Override
    public RecipeCommands convert(@Nullable Recipe source) {
        if (Objects.isNull(source)) return null;
        final var command = new RecipeCommands();
        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPreTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setNotes(notesConverter.convert(source.getNotes()));
        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return command;
    }
}
