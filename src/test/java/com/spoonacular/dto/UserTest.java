package com.spoonacular.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    private static Properties prop;
    private static String userName;
    private static String firstName;
    private static String lastName;
    private static String email;

    @BeforeAll
    static void setHeaders() throws IOException {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();


        prop = new Properties();
        prop.load(new FileInputStream("src/test/resources/spoonacular.properties")); // Загружаем файл с помощью интерфейса FileInputStream

        userName = prop.getProperty("username");
        firstName = prop.getProperty("first.name");
        lastName = prop.getProperty("last.name");
        email = prop.getProperty("email");
    }

    @Test
    @DisplayName("Тестирование класса User")
    void userTestForJson() throws JsonProcessingException {

        User user = new User(userName, firstName, lastName, email);

        String resultJson = new ObjectMapper().writeValueAsString(user);

        System.out.println("INFO: Результирующий JSON: " + resultJson);

        assertThat(resultJson, containsString("tinetoon"));
        assertThat(resultJson, containsString("tinetoon@mail.ru"));
    }

}