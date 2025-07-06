package com.example.unit.exceptions;

import com.example.domain.exceptions.CreditoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Unidade - CreditoNotFoundException")
class CreditoNotFoundExceptionUnitTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        String mensagem = "Crédito não encontrado com número: 123456";

        CreditoNotFoundException exception = new CreditoNotFoundException(mensagem);

        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), 404);
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem e causa")
    void deveCriarExcecaoComMensagemECausa() {
        String mensagem = "Erro ao buscar crédito";
        Throwable causa = new RuntimeException("Erro de banco de dados");

        CreditoNotFoundException exception = new CreditoNotFoundException(mensagem, causa);

        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }
} 