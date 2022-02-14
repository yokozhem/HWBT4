package com.spoonacular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class ListResults {

    // Поле класса
    @JsonProperty("")
    private Recipes main;

    // Пустой конструктор (для работы с библиотекой jackson)
    public ListResults() {
    }

    // Конструктор
    public ListResults(Recipes main) {
        this.main = main;
    }
}
