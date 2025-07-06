package com.example.unit.services;

import com.example.domain.dtos.CreditoDTO;
import com.example.domain.entities.Credito;
import com.example.domain.exceptions.CreditoNotFoundException;
import com.example.domain.mappers.CreditoMapper;
import com.example.repositories.CreditoRepository;
import com.example.services.impl.CreditoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Unidade - CreditoService")
class CreditoServiceUnitTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private CreditoMapper creditoMapper;

    @InjectMocks
    private CreditoServiceImpl creditoService;

    private Credito credito;
    private CreditoDTO creditoDTO;
    private List<Credito> creditosList;

    @BeforeEach
    void setUp() {
        credito = Credito.builder()
                .id(1L)
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 1, 15))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.00"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();

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

        creditosList = Arrays.asList(credito);
    }

    @Test
    @DisplayName("Deve buscar créditos por NFSe com sucesso")
    void deveBuscarCreditosPorNfseComSucesso() {
        String numeroNfse = "7891011";
        when(creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse)).thenReturn(creditosList);
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        List<CreditoDTO> result = creditoService.buscarCreditosPorNfse(numeroNfse);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(creditoDTO, result.get(0));
        assertEquals("123456", result.get(0).getNumeroCredito());
        assertEquals("7891011", result.get(0).getNumeroNfse());
        assertEquals("Sim", result.get(0).getSimplesNacional());

        verify(creditoRepository, times(1)).findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse);
        verify(creditoMapper, times(1)).toDTO(credito);
    }

    @Test
    @DisplayName("Deve buscar créditos por NFSe com sucesso")
    void deveBuscarMultiplosCreditosPorNfseComSucesso() {
        String numeroNfse = "7891011";
        Credito credito2 = Credito.builder()
                .id(2L)
                .numeroCredito("789012")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 1, 20))
                .valorIssqn(new BigDecimal("2000.50"))
                .tipoCredito("ISSQN")
                .simplesNacional(false)
                .aliquota(new BigDecimal("3.00"))
                .valorFaturado(new BigDecimal("40000.00"))
                .valorDeducao(new BigDecimal("8000.00"))
                .baseCalculo(new BigDecimal("32000.00"))
                .build();

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

        List<Credito> multiplosCreditos = Arrays.asList(credito, credito2);
        List<CreditoDTO> multiplosCreditosDTO = Arrays.asList(creditoDTO, creditoDTO2);

        when(creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse)).thenReturn(multiplosCreditos);
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);
        when(creditoMapper.toDTO(credito2)).thenReturn(creditoDTO2);

        List<CreditoDTO> result = creditoService.buscarCreditosPorNfse(numeroNfse);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(multiplosCreditosDTO, result);
        assertEquals("123456", result.get(0).getNumeroCredito());
        assertEquals("789012", result.get(1).getNumeroCredito());
        assertEquals("Sim", result.get(0).getSimplesNacional());
        assertEquals("Não", result.get(1).getSimplesNacional());

        verify(creditoRepository, times(1)).findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse);
        verify(creditoMapper, times(1)).toDTO(credito);
        verify(creditoMapper, times(1)).toDTO(credito2);
    }

    @Test
    @DisplayName("Deve buscar crédito por número com sucesso")
    void deveBuscarCreditoPorNumeroComSucesso() {
        String numeroCredito = "123456";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        CreditoDTO result = creditoService.buscarCreditoPorNumero(numeroCredito);

        assertNotNull(result);
        assertEquals(creditoDTO, result);
        assertEquals("123456", result.getNumeroCredito());
        assertEquals("7891011", result.getNumeroNfse());
        assertEquals("Sim", result.getSimplesNacional());

        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
        verify(creditoMapper, times(1)).toDTO(credito);
    }

    @Test
    @DisplayName("Deve lançar exceção quando NFSe não existe")
    void deveLancarExcecaoQuandoNfseNaoExiste() {
        String numeroNfse = "9999999";
        when(creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse)).thenReturn(Collections.emptyList());

        CreditoNotFoundException exception = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditosPorNfse(numeroNfse);
        });

        assertEquals("Nenhum crédito encontrado para a NFS-e: 9999999", exception.getMessage());
        verify(creditoRepository, times(1)).findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse);
        verify(creditoMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando crédito não existe")
    void deveLancarExcecaoQuandoCreditoNaoExiste() {
        String numeroCredito = "999999";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        CreditoNotFoundException exception = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditoPorNumero(numeroCredito);
        });

        assertEquals("Crédito não encontrado com número: 999999", exception.getMessage());
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
        verify(creditoMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Deve lidar com parâmetros vazios")
    void deveLidarComParametrosVazios() {
        String numeroNfse = "";
        String numeroCredito = "";
        when(creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse)).thenReturn(Collections.emptyList());
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        CreditoNotFoundException exceptionNfse = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditosPorNfse(numeroNfse);
        });

        CreditoNotFoundException exceptionCredito = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditoPorNumero(numeroCredito);
        });

        assertEquals("Nenhum crédito encontrado para a NFS-e: ", exceptionNfse.getMessage());
        assertEquals("Crédito não encontrado com número: ", exceptionCredito.getMessage());

        verify(creditoRepository, times(1)).findByNumeroNfseOrderByDataConstituicaoDesc(numeroNfse);
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    @DisplayName("Deve lidar com parâmetros nulos")
    void deveLidarComParametrosNulos() {
        when(creditoRepository.findByNumeroNfseOrderByDataConstituicaoDesc(null)).thenReturn(Collections.emptyList());
        when(creditoRepository.findByNumeroCredito(null)).thenReturn(Optional.empty());

        CreditoNotFoundException exceptionNfse = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditosPorNfse(null);
        });

        CreditoNotFoundException exceptionCredito = assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.buscarCreditoPorNumero(null);
        });

        assertEquals("Nenhum crédito encontrado para a NFS-e: null", exceptionNfse.getMessage());
        assertEquals("Crédito não encontrado com número: null", exceptionCredito.getMessage());

        verify(creditoRepository, times(1)).findByNumeroNfseOrderByDataConstituicaoDesc(null);
        verify(creditoRepository, times(1)).findByNumeroCredito(null);
    }
} 