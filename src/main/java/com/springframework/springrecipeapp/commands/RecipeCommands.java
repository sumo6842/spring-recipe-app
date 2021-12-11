package com.springframework.springrecipeapp.commands;

import com.springframework.springrecipeapp.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommands {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private Difficulty difficulty;
    private String directions;
    private Set<IngredientsCommand> ingredients = new HashSet<>();
    private NotesCommand notes;
    private Set<CategoriesCommand> categories = new HashSet<>();

}
