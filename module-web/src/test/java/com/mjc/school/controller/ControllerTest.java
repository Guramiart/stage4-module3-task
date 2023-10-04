package com.mjc.school.controller;

import com.mjc.school.web.controller.PathConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private Environment environment;
    @LocalServerPort
    private int port;
    private final Map<String, String> headers = new HashMap<>();

    private final List<String> pathList = new ArrayList<>() {
        {
            add(PathConstants.TAG_PATH);
            add(PathConstants.AUTHOR_PATH);
            add(PathConstants.NEWS_PATH);
            add(PathConstants.COMMENT_PATH);
        }
    };

    @BeforeEach
    protected void setup() throws JSONException {
        RestAssured.baseURI = environment.getProperty("test.server") + port;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "TestUsername");
        jsonObject.put("password", "TestPassword");

        given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post(PathConstants.AUTH_PATH + "/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        String token = given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post(PathConstants.AUTH_PATH + "/authenticate")
                .jsonPath()
                .get("token");

        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + token);
    }

    @Test
    void basicPingTest() {
        pathList.forEach(el -> {
            given().headers(headers).when()
                    .get(el)
                    .then()
                    .assertThat().statusCode(HttpStatus.OK.value());
        });
    }

    void shouldCreateNewEntity() {}

    void shouldFindEntityByProvidedId() {}

    void shouldUpdateEntity() {}

    void shouldDeleteEntityByProvidedId() {}
}
