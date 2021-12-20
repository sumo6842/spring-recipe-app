package com.springframework.springrecipeapp.commands;

import com.springframework.springrecipeapp.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 225)
    private String description;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(99)
    private Integer cookTime;
    @Min(1)
    @Max(99)
    private Integer servings;

    @NotBlank
    private String source;

    private String url;

    private Difficulty difficulty;

    private String directions;

    private Byte[] image;

    private Set<IngredientsCommand> ingredients = new HashSet<>();

    private NotesCommand notes;

    private Set<CategoriesCommand> categories = new HashSet<>();

}
