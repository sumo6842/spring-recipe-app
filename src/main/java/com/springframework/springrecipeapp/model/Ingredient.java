package com.springframework.springrecipeapp.model;

import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor public class Ingredient {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;

    @NonNull
    @OneToOne(fetch = EAGER)
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;

}
