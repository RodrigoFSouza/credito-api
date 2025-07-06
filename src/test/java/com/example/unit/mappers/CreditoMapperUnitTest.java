package com.example.unit.mappers;

import com.example.domain.dtos.CreditoDTO;
import com.example.domain.entities.Credito;
import com.example.domain.mappers.CreditoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Unidade - CreditoMapper")
class CreditoMapperUnitTest {

    private CreditoMapper creditoMapper;
    private Credito credito;
    private CreditoDTO creditoDTO;

    @BeforeEach
    void setUp() {
        creditoMapper = new CreditoMapper();
        
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
    }

    @Test
    @DisplayName("Deve converter entidade para DTO com sucesso")
    void deveConverterEntidadeParaDTOComSucesso() {
        CreditoDTO result = creditoMapper.toDTO(credito);

        assertNotNull(result);
        assertEquals(credito.getId(), result.getId());
        assertEquals(credito.getNumeroCredito(), result.getNumeroCredito());
        assertEquals(credito.getNumeroNfse(), result.getNumeroNfse());
        assertEquals(credito.getDataConstituicao(), result.getDataConstituicao());
        assertEquals(credito.getValorIssqn(), result.getValorIssqn());
        assertEquals(credito.getTipoCredito(), result.getTipoCredito());
        assertEquals("Sim", result.getSimplesNacional());
        assertEquals(credito.getAliquota(), result.getAliquota());
        assertEquals(credito.getValorFaturado(), result.getValorFaturado());
        assertEquals(credito.getValorDeducao(), result.getValorDeducao());
        assertEquals(credito.getBaseCalculo(), result.getBaseCalculo());
    }

    @Test
    @DisplayName("Deve converter DTO para entidade com sucesso")
    void deveConverterDTOParaEntidadeComSucesso() {
        Credito result = creditoMapper.toEntity(creditoDTO);

        assertNotNull(result);
        assertEquals(creditoDTO.getNumeroCredito(), result.getNumeroCredito());
        assertEquals(creditoDTO.getNumeroNfse(), result.getNumeroNfse());
        assertEquals(creditoDTO.getDataConstituicao(), result.getDataConstituicao());
        assertEquals(creditoDTO.getValorIssqn(), result.getValorIssqn());
        assertEquals(creditoDTO.getTipoCredito(), result.getTipoCredito());
        assertEquals(true, result.getSimplesNacional());
        assertEquals(creditoDTO.getAliquota(), result.getAliquota());
        assertEquals(creditoDTO.getValorFaturado(), result.getValorFaturado());
        assertEquals(creditoDTO.getValorDeducao(), result.getValorDeducao());
        assertEquals(creditoDTO.getBaseCalculo(), result.getBaseCalculo());
    }
} 