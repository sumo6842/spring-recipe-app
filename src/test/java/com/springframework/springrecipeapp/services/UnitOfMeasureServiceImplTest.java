package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.springrecipeapp.model.UnitOfMeasure;
import com.springframework.springrecipeapp.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository repository;

    @Mock
    UnitOfMeasureToUnitOfMeasureCommand commandConverter;

    private UnitOfMeasureService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UnitOfMeasureServiceImpl(repository, commandConverter);

    }

    @Test
    void listAllUoms() {
        //given
        var list = new HashSet<UnitOfMeasureCommand>();
        var unitOfMeasure = new UnitOfMeasureCommand();
        unitOfMeasure.setId(1L);
        list.add(unitOfMeasure);
        //when
        when(service.listAllUoms()).thenReturn(list);

        //then
        Mockito.verify(repository).findAll();
    }
}