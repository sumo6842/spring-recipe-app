package com.springframework.springrecipeapp.repository;

import com.springframework.springrecipeapp.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
