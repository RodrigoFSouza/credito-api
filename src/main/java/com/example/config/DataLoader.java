package com.example.config;

import com.example.domain.entities.Credito;
import com.example.repositories.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    
    private final CreditoRepository creditoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (creditoRepository.count() == 0) {
            carregarDadosIniciais();
        }
    }
    
    private void carregarDadosIniciais() {
        Credito credito1 = Credito.builder()
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 25))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.0"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();

        Credito credito2 = Credito.builder()
                .numeroCredito("789012")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 26))
                .valorIssqn(new BigDecimal("1200.50"))
                .tipoCredito("ISSQN")
                .simplesNacional(false)
                .aliquota(new BigDecimal("4.5"))
                .valorFaturado(new BigDecimal("25000.00"))
                .valorDeducao(new BigDecimal("4000.00"))
                .baseCalculo(new BigDecimal("21000.00"))
                .build();

        Credito credito3 = Credito.builder()
                .numeroCredito("654321")
                .numeroNfse("1122334")
                .dataConstituicao(LocalDate.of(2024, 1, 15))
                .valorIssqn(new BigDecimal("800.50"))
                .tipoCredito("Outros")
                .simplesNacional(true)
                .aliquota(new BigDecimal("3.5"))
                .valorFaturado(new BigDecimal("20000.00"))
                .valorDeducao(new BigDecimal("3000.00"))
                .baseCalculo(new BigDecimal("17000.00"))
                .build();
        
        creditoRepository.save(credito1);
        creditoRepository.save(credito2);
        creditoRepository.save(credito3);

        System.out.println("Dados iniciais carregados com sucesso!");
    }
}