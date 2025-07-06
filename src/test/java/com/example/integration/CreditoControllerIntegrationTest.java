package com.example.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;

@DisplayName("Testes de Integração - CreditoController")
class CreditoControllerIntegrationTest extends BaseIntegrationTest {

    private static final String BASE_PATH = "/api/creditos";

    @Test
    @DisplayName("Deve buscar créditos por NFSe com sucesso")
    void deveBuscarCreditosPorNfseComSucesso() {
        String numeroNfse = "7891011";

        Response response = given()
                .when()
                .get(BASE_PATH + "/{numeroNfse}", numeroNfse);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("numeroCredito", containsInAnyOrder("123456", "789012"))
                .body("numeroNfse", everyItem(equalTo("7891011")))
                .body("tipoCredito", everyItem(equalTo("ISSQN")))
                .body("simplesNacional", containsInAnyOrder("Sim", "Não"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando NFSe não existe")
    void deveRetornarListaVaziaQuandoNfseNaoExiste() {
        String numeroNfse = "9999999";

        Response response = given()
                .when()
                .get(BASE_PATH + "/{numeroNfse}", numeroNfse);

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve buscar crédito por número com sucesso")
    void deveBuscarCreditoPorNumeroComSucesso() {
        String numeroCredito = "123456";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("numeroCredito", equalTo("123456"))
                .body("numeroNfse", equalTo("7891011"))
                .body("tipoCredito", equalTo("ISSQN"))
                .body("simplesNacional", equalTo("Sim"))
                .body("valorIssqn", equalTo(1500.75f))
                .body("aliquota", equalTo(5.00f))
                .body("valorFaturado", equalTo(30000.00f))
                .body("valorDeducao", equalTo(5000.00f))
                .body("baseCalculo", equalTo(25000.00f));
    }

    @Test
    @DisplayName("Deve retornar 404 quando crédito não existe")
    void deveRetornar404QuandoCreditoNaoExiste() {
        String numeroCredito = "999999";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve validar formato de resposta JSON")
    void deveValidarFormatoRespostaJson() {
        String numeroCredito = "123456";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("numeroCredito", notNullValue())
                .body("numeroNfse", notNullValue())
                .body("dataConstituicao", notNullValue())
                .body("valorIssqn", notNullValue())
                .body("tipoCredito", notNullValue())
                .body("simplesNacional", anyOf(equalTo("Sim"), equalTo("Não")))
                .body("aliquota", notNullValue())
                .body("valorFaturado", notNullValue())
                .body("valorDeducao", notNullValue())
                .body("baseCalculo", notNullValue());
    }

    @Test
    @DisplayName("Deve buscar múltiplos créditos por NFSe")
    void deveBuscarMultiplosCreditosPorNfse() {
        String numeroNfse = "7891011";

        Response response = given()
                .when()
                .get(BASE_PATH + "/{numeroNfse}", numeroNfse);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("findAll { it.numeroNfse == '7891011' }.size()", equalTo(2))
                .body("findAll { it.tipoCredito == 'ISSQN' }.size()", equalTo(2));
    }

    @Test
    @DisplayName("Deve validar headers da resposta")
    void deveValidarHeadersResposta() {
        String numeroCredito = "123456";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", containsString("application/json"));
    }

    @Test
    @DisplayName("Deve testar endpoint com caracteres especiais")
    void deveTestarEndpointComCaracteresEspeciais() {
        String numeroCredito = "123-456";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve testar endpoint com números longos")
    void deveTestarEndpointComNumerosLongos() {
        String numeroCredito = "12345678901234567890";

        Response response = given()
                .when()
                .get(BASE_PATH + "/credito/{numeroCredito}", numeroCredito);

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve validar estrutura de lista de créditos")
    void deveValidarEstruturaListaCreditos() {
        String numeroNfse = "7891011";

        Response response = given()
                .when()
                .get(BASE_PATH + "/{numeroNfse}", numeroNfse);

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", instanceOf(java.util.List.class))
                .body("[0]", instanceOf(java.util.Map.class))
                .body("numeroCredito", containsInAnyOrder("123456", "789012"))
                .body("numeroNfse", everyItem(equalTo("7891011")))
                .body("tipoCredito", everyItem(equalTo("ISSQN")))
                .body("simplesNacional", containsInAnyOrder("Sim", "Não"));
    }
} 