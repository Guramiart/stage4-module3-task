package com.mjc.school.controller;

import com.mjc.school.web.controller.constants.PathConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Testcontainers
@ActiveProfiles("test-containers")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {
    @LocalServerPort
    int port;
    private final ControllerTestData controllerTestData = new ControllerTestData();
    private static final Map<String, String> headers = new HashMap<>();

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("test-db")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @BeforeAll
    static void start() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stop() {
        postgreSQLContainer.stop();
    }

    @Autowired
    ControllerTest() throws JSONException {
    }

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void authenticate() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "TestUsername");
        jsonObject.put("password", "TestPassword");

        JSONObject adminJsonObject = new JSONObject();
        adminJsonObject.put("username", "admin");
        adminJsonObject.put("password", "admin");

        given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post(PathConstants.AUTH_PATH + "/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
/*
        String token = given()
                .contentType(ContentType.JSON)
                .body(adminJsonObject.toString())
                .when()
                .post(PathConstants.AUTH_PATH + "/authenticate")
                .jsonPath()
                .get("token");

        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + token);

 */
    }

    @Test
    @Order(2)
    void basicPingTest() {
        controllerTestData.getPathList().forEach(el ->
                given().headers(headers).when()
                    .get(el)
                    .then()
                    .assertThat().statusCode(HttpStatus.OK.value()));
    }
/*
    @Test
    @Order(3)
    void shouldCreateNewEntity() {
        final int[] index = {0};
        controllerTestData.getPathList().forEach(path ->
                given().headers(headers)
                    .contentType(ContentType.JSON)
                    .body(controllerTestData.getTestData().get(index[0]++))
                    .when()
                    .post(path)
                    .then()
                    .assertThat().statusCode(HttpStatus.CREATED.value()));
    }

    @Test
    @Order(4)
    void shouldFindEntityByProvidedId() {
        controllerTestData.getPathList().forEach(path ->
                given().headers(headers)
                    .contentType(ContentType.JSON)
                    .pathParam("id", 1)
                    .when()
                    .get(path + PathConstants.ID_PATH)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value()));

        List.of("/authors", "/tags", "/comments").forEach(el ->
                given().headers(headers)
                    .contentType(ContentType.JSON)
                    .pathParam("id", 1)
                    .when()
                    .get(PathConstants.NEWS_PATH + PathConstants.ID_PATH + el)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value()));
    }

    @Test
    @Order(5)
    void shouldUpdateEntity() {
        final int[] index = {0};
        controllerTestData.getPathList().forEach(path ->
                given().headers(headers)
                        .contentType(ContentType.JSON)
                        .body(controllerTestData.getUpdatedTestData().get(index[0]++))
                        .pathParam("id", "1")
                        .when()
                        .patch(path + PathConstants.ID_PATH)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value()));
    }

    @Test
    @Order(6)
    void shouldDeleteEntityByProvidedId() {
        controllerTestData.getDeletionPathList().forEach(path ->
                given().headers(headers)
                        .contentType(ContentType.JSON)
                        .pathParam("id", 1)
                        .when()
                        .delete(path + PathConstants.ID_PATH)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.NO_CONTENT.value()));
    }
*/
}
