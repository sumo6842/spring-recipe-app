package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.CategoriesCommand;
import com.springframework.springrecipeapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryCommandToCategory implements Converter<CategoriesCommand, Category> {

    @Synchronized
    @Override
    public Category convert(@Nullable CategoriesCommand source) {
        if (Objects.isNull(source)) return null;
        final var category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
