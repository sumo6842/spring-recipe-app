package com.springframework.springrecipeapp.repository;

import com.springframework.springrecipeapp.model.Ingredient;
import com.springframework.springrecipeapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Set<Ingredient> findAllByRecipe_Id(Long id);
}
