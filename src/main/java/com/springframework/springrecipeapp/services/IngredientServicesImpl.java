package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.converters.IngredientToIngredientsCommand;
import com.springframework.springrecipeapp.converters.IngredientsCommandToIngredient;
import com.springframework.springrecipeapp.model.Ingredient;
import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.IngredientRepository;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import com.springframework.springrecipeapp.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServicesImpl implements IngredientServices {
    private final IngredientsCommandToIngredient ingredientConverter;
    private final IngredientToIngredientsCommand commandConverter;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;


    private final BiFunction<Recipe, IngredientsCommand, Optional<Ingredient>> toIngredientFunc =
            (recipe, command) -> recipe.getIngredients().stream()
                    .filter(val -> val.getId().equals(command.getId()))
                    .findFirst();

    private final BiFunction<Recipe, Long, Optional<Ingredient>> findIngredient =
            (recipe, ingredientId) -> recipe.getIngredients().stream()
                    .filter(x -> x.getId().equals(ingredientId)).findFirst();

    @Override
    public IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        var recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("recipe not found!!!");
        }
        var command = recipeOptional
                .flatMap(x -> this.findIngredient.apply(x, ingredientId))
                .map(commandConverter::convert);
        if (command.isEmpty()) log.error("Cant found ingredient");
        return command.orElse(new IngredientsCommand());
    }

    @Override
    @Transactional
    public IngredientsCommand saveIngredientCommand(IngredientsCommand command) {
        var recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientsCommand();
        }

        var ingredient = recipeOptional.flatMap(val -> toIngredientFunc.apply(val, command));
        if (ingredient.isPresent()) {
            var result = ingredient.stream()
                    .peek(val -> val.setDescription(command.getDescription()))
                    .peek(val -> val.setAmount(command.getAmount()))
                    .peek(val -> setUom(command, val))
                    .findFirst();
        } else {
            ingredient = Optional.ofNullable(ingredientConverter.convert(command));
            recipeOptional.get().addIngredient(ingredient.orElse(new Ingredient()));
        }
        ingredientRepository.save(ingredient.get());

        var savedRecipe = recipeRepository.save(recipeOptional.get());

        return getIngredientsCommand(command, savedRecipe);
    }

    /**
     * todo convert ingredient to ingredientCommand by recipe
     */
    private IngredientsCommand getIngredientsCommand(IngredientsCommand command, Recipe savedRecipe) {
        var savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                .findFirst();
        if (savedIngredientOptional.isEmpty()) {
            savedIngredientOptional = savedRecipe.getIngredients()
                    .stream()
                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        return commandConverter.convert(savedIngredientOptional.orElse(new Ingredient()));
    }

    private void setUom(IngredientsCommand command, Ingredient val) {
        val.setUom(uomRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("Uom not found")));
    }


    @Override
    public void deleteIngredient(Long recipeId, Long id) {
        var recipeOptional =
                this.recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.debug("recipe is null");
            return;
        }
        var ingredientOptional = recipeOptional.flatMap(val -> val.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst());
        ingredientOptional.ifPresent(val -> {
            log.info("start debug hibernate");
            //todo destroyed link between ingredients and recipe
            val.setRecipe(null);
            recipeOptional.get().getIngredients().remove(val);
            ingredientRepository.save(val);
            recipeRepository.save(recipeOptional.get());
        });
    }


}
