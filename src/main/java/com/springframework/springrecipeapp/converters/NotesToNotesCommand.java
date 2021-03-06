package com.springframework.springrecipeapp.converters;

import com.springframework.springrecipeapp.commands.NotesCommand;
import com.springframework.springrecipeapp.model.Notes;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Override
    public NotesCommand convert(@Nullable Notes source) {
        if (Objects.isNull(source)) return null;
        final var notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        log.info("recipe notes: " + source.getRecipeNotes());
        return notesCommand;
    }
}
