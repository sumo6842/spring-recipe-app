package com.springframework.springrecipeapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;

}
