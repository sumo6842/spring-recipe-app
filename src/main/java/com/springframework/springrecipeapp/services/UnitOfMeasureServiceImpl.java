package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.springrecipeapp.model.UnitOfMeasure;
import com.springframework.springrecipeapp.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand commandConverter;

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(commandConverter::convert)
                .collect(Collectors.toSet());
    }
}
