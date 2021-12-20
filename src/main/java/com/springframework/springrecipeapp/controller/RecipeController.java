package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.exception.NotFoundException;
import com.springframework.springrecipeapp.services.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecipeController {
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        var byId = recipeService.findById(id);
        log.info("RecipeNote:" + byId.getNotes().getRecipeNotes());
        model.addAttribute("recipe", byId);
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand commands,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand saved = recipeService.saveRecipeCommand(commands);
        return "redirect:/recipe/" + saved.getId() + "/show/";
    }

    @GetMapping("recipe/{id}/delete")
    public String deletedById(@PathVariable Long id) {
        log.info("Deleting id: " + id);
        recipeService.deleteByIdLong(id);
        return "redirect:/";
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.info("Error");
        log.info("exception: " + exception.getMessage());
        log.error("Handling not found Exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
