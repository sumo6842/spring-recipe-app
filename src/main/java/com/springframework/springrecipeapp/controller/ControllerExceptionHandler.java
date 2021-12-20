package com.springframework.springrecipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView badRequestException(Exception exception) {
        String result = exception.getMessage()
                .substring(exception.getMessage().indexOf("For input"));
        log.error("exception: {}", result);
        var modelAndView = new ModelAndView();
        modelAndView.setViewName("400");
        modelAndView.addObject("exception", result);
        return modelAndView;
    }
}
