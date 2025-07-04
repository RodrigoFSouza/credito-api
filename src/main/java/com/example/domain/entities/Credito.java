package com.example.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_credito", nullable = false, length = 50)
    private String numeroCredito;

    @Column(name = "numero_nfse", nullable = false, length = 50)
    private String numeroNfse;

    @Column(name = "data_constituicao", nullable = false)
    private LocalDate dataConstituicao;

    @Column(name = "valor_issqn", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorIssqn;

    @Column(name = "tipo_credito", nullable = false, length = 50)
    private String tipoCredito;

    @Column(name = "simples_nacional", nullable = false)
    private Boolean simplesNacional;

    @Column(name = "aliquota", nullable = false, precision = 5, scale = 2)
    private BigDecimal aliquota;

    @Column(name = "valor_faturado", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorFaturado;

    @Column(name = "valor_deducao", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorDeducao;

    @Column(name = "base_calculo", nullable = false, precision = 15, scale = 2)
    private BigDecimal baseCalculo;
}
