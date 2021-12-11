package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.model.Ingredient;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class IngredientsCommandToIngredient implements Converter<IngredientsCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Synchronized
    @Override
    public Ingredient convert(@Nullable IngredientsCommand source) {
        if (Objects.isNull(source)) return null;
        final var ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        var uom = source.getUom();
        ingredient.setUom(Objects.requireNonNull(uomConverter.convert(uom)));
        return ingredient;
    }
}
