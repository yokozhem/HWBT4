package com.spoonacular;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoonacular.dto.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;



public class MealPlanningTest {


    private static String apiKey;
    private static Properties prop;


    private static String host;
    private static String day;
    private static String hash;


    private static String hostMealPlanning;
    private static String userName;
    private static String firstName;
    private static String lastName;
    private static String email;


    private static Integer mealPlanId;

    @BeforeAll
    static void setHeaders() throws IOException {


        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/spoonacular.properties")); // Загружаем файл с помощью интерфейса FileInputStream


        apiKey = prop.getProperty("api.key");
        host = prop.getProperty("host");
        hostMealPlanning = prop.getProperty("host") + prop.getProperty("meal.planner");
        day = prop.getProperty("data.day.planning");
        hash = prop.getProperty("hash");


        userName = prop.getProperty("username");
        firstName = prop.getProperty("first.name");
        lastName = prop.getProperty("last.name");
        email = prop.getProperty("email");


        mealPlanId = Integer.valueOf(prop.getProperty("test.meal.id"));
    }

    @Disabled("Do not run")
    @Test
    @DisplayName("Подключить пользователя")
    void connectUser() throws IOException {


        User user = new User(userName, firstName, lastName, email);


        String bodyJson = new ObjectMapper().writeValueAsString(user);

        given()
//                .log().all() // Логирование запроса
                .log().method()
                .log().uri()
                .contentType (ContentType.JSON)
                .body(bodyJson)
                .when()
                .post(host + "users/connect" + "?apiKey=" + apiKey)
                .prettyPeek() // Логирование ответа
                .then()
                .statusCode(200);
    }

    @Disabled("Do not run")
    @Test
    @DisplayName("День плана питания")
    void getMealPlanDay() {

        given()
                .log().all() // Логирование запроса
//                .log().method()
//                .log().uri()
                .param("apiKey", apiKey)
//                .param("username", userName)
                .param("hash", hash)
                .when()
                .get(hostMealPlanning + userName + "/day" + "/" + day)
                .prettyPeek() // Логирование ответа
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получить шаблоны плана питания")
    void getMealPlanTemplates() throws IOException {

        String response = given()

                .log().method()
                .log().uri()
                .param("apiKey", apiKey)
                .param("hash", hash)
                .when()
                .get(hostMealPlanning + userName + "/templates")

                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .asString();


        JsonNode node = Json.parse(response);

        Integer recipeId = node.get("templates").get(0).get("id").intValue();
        String recipeName = node.get("templates").get(0).get("name").textValue();

        System.out.println("INFO: ID плана: " + recipeId);
        System.out.println("INFO: Name плана: " + recipeName);

        assertThat(recipeId, equalTo(4437));
        assertThat(recipeName, equalTo("My new meal plan template"));

    }

    @Test
    @DisplayName("Получить шаблон плана питания № 4437")
    void getMealPlanTemplate() throws IOException {

        String response = given()

                .log().method()
                .log().uri()
                .param("apiKey", apiKey)
                .param("hash", hash)
                .when()
                .get(hostMealPlanning + userName + "/templates/" + mealPlanId)

                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .asString();


        JsonNode node = Json.parse(response);

        Integer recipeId = node.get("id").intValue();
        String recipeName = node.get("name").textValue();
        String nameDrinkFifthDay = node.get("days").get(4).get("items").get(0).get("value").get("ingredients").get(1).get("name").textValue();

        System.out.println("INFO: ID плана: " + recipeId);
        System.out.println("INFO: Name плана: " + recipeName);
        System.out.println("INFO: Тип напитка: " + nameDrinkFifthDay);

        assertThat(recipeId, equalTo(4437));
        assertThat(recipeName, equalTo("My new meal plan template"));
        assertThat(nameDrinkFifthDay, equalTo("coffee"));
    }
}
