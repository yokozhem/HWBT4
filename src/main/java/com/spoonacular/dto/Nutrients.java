package com.spoonacular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class Nutrients {

    @JsonProperty("name")
    private String nutrientsName;
    @JsonProperty("amount")
    private Double nutrientsAmount;
    @JsonProperty("unit")
    private String unit;
}
