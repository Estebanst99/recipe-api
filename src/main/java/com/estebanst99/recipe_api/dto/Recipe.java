package com.estebanst99.recipe_api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    private Long id;
    private String title;
    private String description;
    private List<String> ingredients;
}