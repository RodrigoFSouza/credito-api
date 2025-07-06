package com.example.integration;

import com.example.domain.entities.Credito;
import com.example.repositories.CreditoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected CreditoRepository creditoRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        creditoRepository.deleteAll();
        
        insertTestData();
    }

    protected void insertTestData() {
        List<Credito> creditos = Arrays.asList(
            Credito.builder()
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 25))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.00"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build(),
            
            Credito.builder()
                .numeroCredito("789012")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 26))
                .valorIssqn(new BigDecimal("1200.50"))
                .tipoCredito("ISSQN")
                .simplesNacional(false)
                .aliquota(new BigDecimal("4.50"))
                .valorFaturado(new BigDecimal("25000.00"))
                .valorDeducao(new BigDecimal("4000.00"))
                .baseCalculo(new BigDecimal("21000.00"))
                .build(),
            
            Credito.builder()
                .numeroCredito("654321")
                .numeroNfse("1122334")
                .dataConstituicao(LocalDate.of(2024, 1, 15))
                .valorIssqn(new BigDecimal("800.50"))
                .tipoCredito("Outros")
                .simplesNacional(true)
                .aliquota(new BigDecimal("3.50"))
                .valorFaturado(new BigDecimal("20000.00"))
                .valorDeducao(new BigDecimal("3000.00"))
                .baseCalculo(new BigDecimal("17000.00"))
                .build(),
            
            Credito.builder()
                .numeroCredito("987654")
                .numeroNfse("5566778")
                .dataConstituicao(LocalDate.of(2024, 3, 10))
                .valorIssqn(new BigDecimal("2200.00"))
                .tipoCredito("ISSQN")
                .simplesNacional(false)
                .aliquota(new BigDecimal("6.00"))
                .valorFaturado(new BigDecimal("40000.00"))
                .valorDeducao(new BigDecimal("6000.00"))
                .baseCalculo(new BigDecimal("34000.00"))
                .build()
        );

        creditoRepository.saveAll(creditos);
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    protected io.restassured.specification.RequestSpecification given() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
} 