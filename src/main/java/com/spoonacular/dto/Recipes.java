package com.spoonacular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class Recipes {

    @JsonProperty("id")
    private String recipesId;
    @JsonProperty("title")
    private String recipesName;
    @JsonProperty("image")
    private String recipesUrlImage;
    @JsonProperty("nutrition")
    private Nutrition nutrition;


    public Recipes() {
    }

    public Recipes(String recipesId, String recipesName, String recipesUrlImage, Nutrition nutrition) {
        this.recipesId = recipesId;
        this.recipesName = recipesName;
        this.recipesUrlImage = recipesUrlImage;
        this.nutrition = nutrition;
    }
}
