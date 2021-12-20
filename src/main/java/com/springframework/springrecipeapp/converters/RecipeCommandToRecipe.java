package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.model.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
   private final CategoryCommandToCategory categoryCommandToCategory;
   private final IngredientsCommandToIngredient ingredientsCommandToIngredient;
   private final NotesCommandToNotes notesCommandToNotes;

    @Synchronized
    @Override
    public Recipe convert(@Nullable RecipeCommand source) {
        if (source == null ) return null;
        final var recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPreTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(Objects.requireNonNull(notesCommandToNotes.convert(source.getNotes())));
        if (source.getCategories().size() > 0) {
            source.getCategories()
                    .stream().map(categoryCommandToCategory::convert)
                    .forEach(recipe.getCategories()::add);
        }
        if (source.getIngredients().size() > 0) {
            source.getIngredients()
                    .stream().map(ingredientsCommandToIngredient::convert)
                    .forEach(recipe.getIngredients()::add);
        }
        return recipe;
    }
}
