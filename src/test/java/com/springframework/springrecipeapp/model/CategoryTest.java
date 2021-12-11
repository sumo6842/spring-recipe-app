package com.springframework.springrecipeapp.model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
         category.setId(4L);
         assertEquals(4L, category.getId());
    }

    @Test
    public void getDescription() {

    }

    @Test
    public void getRecipes() {

    }
}