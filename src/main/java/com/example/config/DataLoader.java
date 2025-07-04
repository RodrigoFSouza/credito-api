package com.example.config;

import com.example.repositories.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private CreditoRepository creditoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem dados na tabela
        if (creditoRepository.count() == 0) {
            carregarDadosIniciais();
        }
    }
    
    private void carregarDadosIniciais() {
        // Dados de exemplo conforme o script fornecido
        /*
        Credito credito1 = new Credito(
            "123456",
            "7891011",
            LocalDate.of(2024, 2, 25),
            new BigDecimal("1500.75"),
            "ISSQN",
            true,
            new BigDecimal("5.0"),
            new BigDecimal("30000.00"),
            new BigDecimal("5000.00"),
            new BigDecimal("25000.00")
        );
        
        Credito credito2 = new Credito(
            "789012",
            "7891011",
            LocalDate.of(2024, 2, 26),
            new BigDecimal("1200.50"),
            "ISSQN",
            false,
            new BigDecimal("4.5"),
            new BigDecimal("25000.00"),
            new BigDecimal("4000.00"),
            new BigDecimal("21000.00")
        );
        
        Credito credito3 = new Credito(
            "654321",
            "1122334",
            LocalDate.of(2024, 1, 15),
            new BigDecimal("800.50"),
            "Outros",
            true,
            new BigDecimal("3.5"),
            new BigDecimal("20000.00"),
            new BigDecimal("3000.00"),
            new BigDecimal("17000.00")
        );
        
        creditoRepository.save(credito1);
        creditoRepository.save(credito2);
        creditoRepository.save(credito3);
        */
        System.out.println("Dados iniciais carregados com sucesso!");
    }
}