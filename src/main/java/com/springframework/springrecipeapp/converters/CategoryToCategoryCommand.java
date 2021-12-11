package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.CategoriesCommand;
import com.springframework.springrecipeapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoriesCommand> {

    @Synchronized
    @Override
    public CategoriesCommand convert(@Nullable Category source) {
        if (Objects.isNull(source)) {
            return null;
        }
        final var categoryCommand = new CategoriesCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());
        return categoryCommand;
    }
}
