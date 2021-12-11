package com.springframework.springrecipeapp.repository;

import com.springframework.springrecipeapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
