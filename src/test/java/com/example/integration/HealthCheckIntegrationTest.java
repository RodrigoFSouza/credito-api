package com.example.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Testes de Health Check")
class HealthCheckIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve retornar status UP para health check")
    void deveRetornarStatusUpParaHealthCheck() {
        Response response = given()
                .when()
                .get("/actuator/health");

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo("UP"))
                .body("components.db.status", equalTo("UP"))
                .body("components.diskSpace.status", equalTo("UP"));
    }

    @Test
    @DisplayName("Deve retornar detalhes do health check")
    void deveRetornarDetalhesDoHealthCheck() {
        Response response = given()
                .when()
                .get("/actuator/health");

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("status", notNullValue())
                .body("components", notNullValue())
                .body("components.db", notNullValue())
                .body("components.diskSpace", notNullValue());
    }
} 