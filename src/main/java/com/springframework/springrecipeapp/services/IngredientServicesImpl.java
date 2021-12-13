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

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServicesImpl implements IngredientServices {
    private final IngredientsCommandToIngredient ingredientConverter;
    private final IngredientToIngredientsCommand commandConverter;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;

    @Override
    public IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        var recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("recipe not found!!!");
        }

        var command = recipeOptional.flatMap(x -> x.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(commandConverter::convert)
                .findFirst());
        return command.orElse(new IngredientsCommand());
    }

    @Override
    @Transactional
    public IngredientsCommand saveIngredientCommand(IngredientsCommand command) {
        var recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientsCommand();
        } else {
            var ingredient = recipeOptional.flatMap(
                    recipe -> recipe.getIngredients()
                            .stream()
                            .filter(val -> val.getId().equals(command.getId()))
                            .findFirst());
            if (ingredient.isPresent()) {
                var result = ingredient.stream()
                        .peek(val -> val.setDescription(command.getDescription()))
                        .peek(val -> val.setAmount(command.getAmount()))
                        //find and save UOM
                        .peek(val -> setUom(command, val))
                        .findFirst();
            } else {
                var ingre = ingredientConverter.convert(command);
//                ingre.setRecipe(recipeOptional.get());
                recipeOptional.get().addIngredient(ingre);
            }
        }


        var savedRecipe = recipeRepository.save(recipeOptional.get());
        var savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                .findFirst();
        if (savedIngredientOptional.isEmpty()) {
            savedIngredientOptional = savedRecipe.getIngredients()
                    .stream()
                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredient -> recipeIngredient.getUom().getId()
                            .equals(command.getUom().getId()))
                    .findFirst();
        }

        return commandConverter.convert(savedIngredientOptional.get());
    }

    private void setUom(IngredientsCommand command, Ingredient val) {
        val.setUom(uomRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("Uom not found")));
    }
}
