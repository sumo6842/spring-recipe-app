package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.IngredientsCommand;
import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import com.springframework.springrecipeapp.services.IngredientServices;
import com.springframework.springrecipeapp.services.RecipeService;
import com.springframework.springrecipeapp.services.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IngredientsController {
    private final IngredientServices ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService uomService;

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredients list for recipe id: " + recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable Long recipeId,
                                       @PathVariable Long id, Model model) {
        var ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable Long recipeId, Model model) {

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  uomService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long id, Model model) {
        var ingredientModel = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("ingredient", ingredientModel);
        model.addAttribute("uomList", uomService.listAllUoms());
        log.info("This is ingredient: " + ingredientModel.getRecipeId());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientsCommand command) {
        IngredientsCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.info("saved recipe id: " + savedCommand.getRecipeId());
        log.info("saved ingredient id: " + savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long id,
                                   @PathVariable Long recipeId) {
        ingredientService.deleteIngredient(recipeId, id);
        return "redirect:/recipe/" + recipeId +"/ingredients";
    }

}
