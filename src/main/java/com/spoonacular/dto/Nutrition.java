package com.spoonacular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class Nutrition {


    @JsonProperty("nutrients")
    private List<Nutrients> nutrients;


    public Nutrition() {
    }


    public Nutrition(List<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }
}
