package com.example.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("Testes de Erro - CreditoController")
class CreditoControllerErrorTest extends BaseIntegrationTest {
    private static final String BASE_PATH = "/api/creditos";

    @Test
    @DisplayName("Deve retornar 404 para endpoint inexistente")
    void deveRetornar404ParaEndpointInexistente() {
        Response response = given()
                .when()
                .get(BASE_PATH + "/endpoint/inexistente");

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve testar endpoint com parâmetro vazio")
    void deveTestarEndpointComParametroVazio() {
        Response response = given()
                .when()
                .get(BASE_PATH + "/");

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve testar endpoint com parâmetro nulo")
    void deveTestarEndpointComParametroNulo() {
        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/");
        
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
} 