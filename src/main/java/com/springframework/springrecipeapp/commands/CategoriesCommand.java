package com.springframework.springrecipeapp.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriesCommand {
    private Long id;
    private String description;
}
