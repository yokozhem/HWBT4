package com.spoonacular;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class RecipesTest {


    private static String apiKey;
    private static Properties prop;


    private static String host;

    @BeforeAll
    static void setHeaders() throws IOException {


        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/spoonacular.properties")); // Загружаем файл с помощью интерфейса FileInputStream

        apiKey = prop.getProperty("api.key");
        host = prop.getProperty("host") + prop.getProperty("recipes");


    }

    @Test
    @DisplayName("Авторизация")
    void authenticationTest() {
        given()
//                .log().all() // Логирование запроса
                .param("apiKey", apiKey)
                .param("includeNutrition", true)
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
//                .prettyPeek() // Логирование ответа
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Поиск рецепта")
    void searchRecipesTest() throws IOException {
        String response = given()
//                .log().all() // Логирование запроса
                .log().method()
                .log().uri()
                .param("apiKey", apiKey)
                .param("query", "pasta")
                .param("maxFat", 25)
                .param("number", 1)
                .when()
                .get(host + "complexSearch")
//                .prettyPeek() // Логирование ответа
                .then()
                .statusCode(200)
                .extract()
                .response()
                        .body()
                                .asString();


        JsonNode node = Json.parse(response);
        /*System.out.println("!!!INFO: Количество результатов поиска: " + node.get("totalResults"));
        System.out.println("!!!INFO: Results: " + node.get("results"));
        System.out.println("!!!INFO: ID: " + node.get("results").get(0).get("id"));
        System.out.println("!!!INFO: Количество жира : " + node.get("results").get(0).get("nutrition").get("nutrients").get(0).get("amount"));
        System.out.println("!!!INFO: Количество жира: " + node.get("results.[0].nutrition.nutrients.amount").toString());*/


        Double amount = node
                .get("results")
                .get(0)
                .get("nutrition")
                .get("nutrients")
                .get(0)
                .get("amount")
                .asDouble();
        Integer number = node.get("number").asInt();

        assertTrue(amount <= 25);
        assertTrue(number == 1);
    }

    @Test
    @DisplayName("Поиск рецептов по питательным веществам")
    void searchRecipesByNutrients() throws IOException {

        String response = given()
//                .log().all() // Логирование запроса
                .log().method()
                .log().uri()
                .param("apiKey", apiKey)
                .param("minVitaminE", 15)
                .param("number", 3)
                .when()
                .get(host + "findByNutrients")
//                .prettyPeek() // Логирование ответа
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .asString();

        JsonNode node = Json.parse(response);
        /*System.out.println("!!!INFO: Количество Витамина Е:" +
                "\n- рецепт № 1: " + node.get(0).get("vitaminE") +
                "\n- рецепт № 2: " + node.get(1).get("vitaminE") +
                "\n- рецепт № 3: " + node.get(2).get("vitaminE"));*/

        /*String value = node.get(0).get("vitaminE").toString();
        Integer vitaminE = Integer.valueOf(value.replaceAll("[\"mg]", ""));
        System.out.println("!!!INFO: Количество Витамина Е в рецепте № 1: " + value);*/

        Integer vitaminE_01 = Integer.valueOf(node.get(0).get("vitaminE").toString().replaceAll("[\"mg]", ""));
        Integer vitaminE_02 = Integer.valueOf(node.get(1).get("vitaminE").toString().replaceAll("[\"mg]", ""));
        Integer vitaminE_03 = Integer.valueOf(node.get(2).get("vitaminE").toString().replaceAll("[\"mg]", ""));

        /*System.out.println("!!!INFO: Количество Витамина Е:" +
                "\n- рецепт № 1: " + vitaminE_01 +
                "\n- рецепт № 2: " + vitaminE_02 +
                "\n- рецепт № 3: " + vitaminE_03);*/

        assertTrue(vitaminE_01 >= 15);
        assertTrue(vitaminE_02 >= 15);
        assertTrue(vitaminE_03 >= 15);
    }

    @Test
    @DisplayName("Получить информацию о рецепте")
    void getRecipeInformation() throws IOException {

        String response = given()

                .log().method()
                .log().uri()
                .param("apiKey", apiKey)
                .param("includeNutrition", false)
                .when()
                .get(host + prop.getProperty("test.recipes.id") + "/information")

                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .asString();


        JsonNode node = Json.parse(response);



        String recipeName = node.get("title").textValue();
        Integer recipeId = node.get("id").intValue();


        assertThat(recipeName, containsString("Pasta"));
        assertThat(recipeId, equalTo(716429));
    }
}
