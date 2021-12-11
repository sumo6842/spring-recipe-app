package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.model.UnitOfMeasure;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UnitOfMeasureToUnitOfMeasureCommand
        implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Synchronized
    @Override
    public UnitOfMeasureCommand convert(@Nullable UnitOfMeasure uom) {
        if (Objects.isNull(uom)) return null;
        final var uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(uom.getId());
        uomCommand.setDescription(uom.getDescription());
        return uomCommand;
    }
}
