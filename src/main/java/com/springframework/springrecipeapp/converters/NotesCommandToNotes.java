package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.NotesCommand;
import com.springframework.springrecipeapp.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Override
    public Notes convert( @Nullable NotesCommand source) {
        if (Objects.isNull(source)) return null;

        final var notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
