package com.example.unit.controllers;

import com.example.controllers.CreditoController;
import com.example.domain.dtos.CreditoDTO;
import com.example.domain.exceptions.CreditoNotFoundException;
import com.example.services.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Unidade - CreditoController")
class CreditoControllerUnitTest {

    @Mock
    private CreditoService creditoService;

    @InjectMocks
    private CreditoController creditoController;

    private CreditoDTO creditoDTO;
    private List<CreditoDTO> creditosList;

    @BeforeEach
    void setUp() {
        creditoDTO = new CreditoDTO(
            1L,
            "123456",
            "7891011",
            LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"),
            "ISSQN",
            "Sim",
            new BigDecimal("5.00"),
            new BigDecimal("30000.00"),
            new BigDecimal("5000.00"),
            new BigDecimal("25000.00")
        );

        creditosList = Arrays.asList(creditoDTO);
    }

    @Test
    @DisplayName("Deve buscar créditos por NFSe com sucesso")
    void deveBuscarCreditosPorNfseComSucesso() {
        String numeroNfse = "7891011";
        when(creditoService.buscarCreditosPorNfse(numeroNfse)).thenReturn(creditosList);

        ResponseEntity<List<CreditoDTO>> response = creditoController.buscarCreditosPorNfse(numeroNfse);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creditosList, response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("123456", response.getBody().get(0).getNumeroCredito());
        
        verify(creditoService, times(1)).buscarCreditosPorNfse(numeroNfse);
    }

    @Test
    @DisplayName("Deve buscar créditos por NFSe com sucesso")
    void deveBuscarMultiplosCreditosPorNfseComSucesso() {
        String numeroNfse = "7891011";
        CreditoDTO creditoDTO2 = new CreditoDTO(
            2L,
            "789012",
            "7891011",
            LocalDate.of(2024, 1, 20),
            new BigDecimal("2000.50"),
            "ISSQN",
            "Não",
            new BigDecimal("3.00"),
            new BigDecimal("40000.00"),
            new BigDecimal("8000.00"),
            new BigDecimal("32000.00")
        );
        List<CreditoDTO> multiplosCreditos = Arrays.asList(creditoDTO, creditoDTO2);
        when(creditoService.buscarCreditosPorNfse(numeroNfse)).thenReturn(multiplosCreditos);

        ResponseEntity<List<CreditoDTO>> response = creditoController.buscarCreditosPorNfse(numeroNfse);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(multiplosCreditos, response.getBody());
        assertEquals(2, response.getBody().size());
        
        verify(creditoService, times(1)).buscarCreditosPorNfse(numeroNfse);
    }

    @Test
    @DisplayName("Deve buscar crédito por número com sucesso")
    void deveBuscarCreditoPorNumeroComSucesso() {
        String numeroCredito = "123456";
        when(creditoService.buscarCreditoPorNumero(numeroCredito)).thenReturn(creditoDTO);

        ResponseEntity<CreditoDTO> response = creditoController.buscarCreditoPorNumero(numeroCredito);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creditoDTO, response.getBody());
        assertEquals("123456", response.getBody().getNumeroCredito());
        assertEquals("7891011", response.getBody().getNumeroNfse());
        assertEquals("Sim", response.getBody().getSimplesNacional());
        
        verify(creditoService, times(1)).buscarCreditoPorNumero(numeroCredito);
    }

    @Test
    @DisplayName("Deve lançar exceção quando NFSe não existe")
    void deveLancarExcecaoQuandoNfseNaoExiste() {
        String numeroNfse = "9999999";
        when(creditoService.buscarCreditosPorNfse(numeroNfse))
                .thenThrow(new CreditoNotFoundException("Nenhum crédito encontrado para a NFS-e: " + numeroNfse));

        CreditoNotFoundException exception = assertThrows(CreditoNotFoundException.class, () -> {
            creditoController.buscarCreditosPorNfse(numeroNfse);
        });

        assertEquals("Nenhum crédito encontrado para a NFS-e: 9999999", exception.getMessage());
        verify(creditoService, times(1)).buscarCreditosPorNfse(numeroNfse);
    }

    @Test
    @DisplayName("Deve lançar exceção quando crédito não existe")
    void deveLancarExcecaoQuandoCreditoNaoExiste() {
        String numeroCredito = "999999";
        when(creditoService.buscarCreditoPorNumero(numeroCredito))
                .thenThrow(new CreditoNotFoundException("Crédito não encontrado com número: " + numeroCredito));

        CreditoNotFoundException exception = assertThrows(CreditoNotFoundException.class, () -> {
            creditoController.buscarCreditoPorNumero(numeroCredito);
        });

        assertEquals("Crédito não encontrado com número: 999999", exception.getMessage());
        verify(creditoService, times(1)).buscarCreditoPorNumero(numeroCredito);
    }
} 