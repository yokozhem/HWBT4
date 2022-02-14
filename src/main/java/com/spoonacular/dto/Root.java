package com.spoonacular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class Root {


    @JsonProperty("results")
    private List<ListResults> listResults;
    @JsonProperty("number")
    private Integer recipesCount;
    @JsonProperty("totalResults")
    private Integer totalResults;


    public Root() {
    }


    public Root(List<ListResults> listResults, Integer recipesCount, Integer totalResults) {
        this.listResults = listResults;
        this.recipesCount = recipesCount;
        this.totalResults = totalResults;
    }
}
